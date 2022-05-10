package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.model.Label
import ducletran.tech.imutransformer.ui.components.RadioList
import ducletran.tech.imutransformer.ui.components.RadioRowData
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import ducletran.tech.imutransformer.utils.FileHelper
import java.io.File
import java.io.FileOutputStream

sealed class CustomExperimentSetupState {
    data class Selection(
        val availablePhoneLabels: List<Label> = listOf(
            Label.UsingInHand, Label.LyingOnDesk, Label.InsideTheBag,
            Label.InsidePantPocket, Label.HoldingInHand
        ),
        val availableHumanActivityLabels: List<Label> = listOf(
            Label.Walking, Label.Standing, Label.Sitting
        )
    ) : CustomExperimentSetupState()

    data class Running(
        val phoneStateLabel: Label,
        val humanActivityLabel: Label,
        val preparationTime: Long,
        val runningTime: Long
    ) : CustomExperimentSetupState()

    object Finish : CustomExperimentSetupState()
}

@Composable
fun CustomExperimentSetupScreenWithNav(
    navController: NavController
) {
    CustomExperimentSetupScreen(modifier = Modifier.fillMaxSize())
}

@Composable
private fun CustomExperimentSetupScreen(modifier: Modifier = Modifier) {
    var state by remember {
        mutableStateOf<CustomExperimentSetupState>(CustomExperimentSetupState.Selection())
    }
    when (val currentState = state) {
        is CustomExperimentSetupState.Selection -> {
            SetupScreen(
                modifier = modifier,
                state = currentState,
                onConfirm = { phoneState, activity, delayTime, runningTime ->
                    state = CustomExperimentSetupState.Running(
                        phoneStateLabel = phoneState,
                        humanActivityLabel = activity,
                        preparationTime = delayTime * 1000,
                        runningTime = runningTime * 1000
                    )
                }
            )
        }
        is CustomExperimentSetupState.Finish -> FinishExperimentScreen(modifier = modifier)
        is CustomExperimentSetupState.Running -> {
            val context = LocalContext.current
            val externalCacheFile =
                File(
                    context.getExternalFilesDir(null),
                    FileHelper.randomFileName(label = currentState.phoneStateLabel)
                )
            val stream = FileOutputStream(externalCacheFile)
            stream.write(FileHelper.columnNames.toByteArray())
            stream.write("\n".toByteArray())

            SensorDataCollectionScreen(
                modifier = modifier,
                preparationTime = currentState.preparationTime,
                runningTime = currentState.runningTime,
                onRunFinished = {
                    stream.close()
                    state = CustomExperimentSetupState.Finish
                },
                onSensorDataCollected = {
                    stream.write(
                        FileHelper.formatSensorData(
                            sensorData = it,
                            activityLabel = currentState.humanActivityLabel,
                            phoneStateLabel = currentState.phoneStateLabel
                        ).toByteArray()
                    )
                    stream.write("\n".toByteArray())
                }
            )
        }
    }
}

@Composable
private fun SetupScreen(
    modifier: Modifier = Modifier,
    state: CustomExperimentSetupState.Selection,
    onConfirm: (Label, Label, Long, Long) -> Unit
) {
    var initialDelayTime by remember { mutableStateOf(10L) }
    var runningTime by remember { mutableStateOf(60L) }
    var selectedPhoneLabel by remember { mutableStateOf<Label?>(null) }
    var selectedHumanActivityLabel by remember { mutableStateOf<Label?>(null) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.select_phone_state),
            fontWeight = FontWeight.Bold
        )
        RadioList(items = state.availablePhoneLabels.map {
            RadioRowData(
                description = it.name,
                isSelected = selectedPhoneLabel == it,
                onClick = {
                    selectedPhoneLabel = it
                }
            )
        })
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.select_human_activity),
            fontWeight = FontWeight.Bold
        )
        RadioList(items = state.availableHumanActivityLabels.map {
            RadioRowData(
                description = it.name,
                isSelected = selectedHumanActivityLabel == it,
                onClick = {
                    selectedHumanActivityLabel = it
                }
            )
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (initialDelayTime == 0L) "" else initialDelayTime.toString(),
            onValueChange = {
                if (it.isEmpty()) {
                    initialDelayTime = 0L
                } else {
                    it.toLongOrNull()?.let { initialDelayTime = it }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(text = stringResource(id = R.string.delay_time_hint))
            },
            label = {
                Text(text = stringResource(id = R.string.delay_start_time))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (runningTime == 0L) "" else runningTime.toString(),
            onValueChange = {
                if (it.isEmpty()) {
                    runningTime = 0L
                } else {
                    it.toLongOrNull()?.let { runningTime = it }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(text = stringResource(id = R.string.running_time_hint))
            },
            label = {
                Text(text = stringResource(id = R.string.running_time))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onConfirm(
                        requireNotNull(selectedPhoneLabel),
                        requireNotNull(selectedHumanActivityLabel),
                        initialDelayTime,
                        runningTime
                    )
                },
                enabled = runningTime > 0L &&
                        initialDelayTime > 0L &&
                        selectedHumanActivityLabel != null &&
                        selectedPhoneLabel != null
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewSelection() {
    IMUTransformerTheme {
        SetupScreen(
            state = CustomExperimentSetupState.Selection(),
            onConfirm = { _, _, _, _ ->

            }
        )
    }
}
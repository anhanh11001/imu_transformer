package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.utils.FileHelper
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

sealed class StepTrackingState {
    object Setup : StepTrackingState()
    object Finish : StepTrackingState()
    data class Running(val fileName: String) : StepTrackingState()
}

@Composable
fun StepTrackingScreenWithNav(navController: NavController) {
    StepTrackingScreen(modifier = Modifier.fillMaxSize())
}

@Composable
private fun StepTrackingSetupScreen(
    modifier: Modifier = Modifier,
    onStart: (String) -> Unit
) {
    var fileName by rememberSaveable { mutableStateOf("") }

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.tracking_not_started), fontSize = 22.sp)
            OutlinedTextField(
                modifier = Modifier.padding(top = 16.dp),
                value = fileName,
                onValueChange = { fileName = it.trim() },
                placeholder = { Text(stringResource(id = R.string.enter_file_name)) },
                label = { Text(stringResource(id = R.string.file_name)) }
            )
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            onClick = {
                if (fileName.isNotBlank()) {
                    onStart(fileName)
                }
            },
            text = { Text(stringResource(id = R.string.start_tracking)) },
            icon = { Icon(Icons.Filled.PlayArrow, null) }
        )
    }
}

@Composable
private fun StepTrackingRunningScreen(
    modifier: Modifier = Modifier,
    onNewStepCounted: () -> Unit,
    onFinish: () -> Unit
) {
    var stepCount by rememberSaveable { mutableStateOf(0L) }


    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            text = stringResource(id = R.string.step_counted, stepCount.toInt()),
            fontSize = 22.sp
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    stepCount += 1
                    onNewStepCounted()
                },
                text = { Text(stringResource(id = R.string.add_new_step)) },
                icon = { Icon(Icons.Filled.PlayArrow, null) }
            )
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = { onFinish() }
            ) {
                Text(text = stringResource(R.string.stop))
            }
        }
    }

}

@Composable
private fun StepTrackingScreen(
    modifier: Modifier = Modifier
) {
    var state by remember {
        mutableStateOf<StepTrackingState>(StepTrackingState.Setup)
    }

    when (state) {
        StepTrackingState.Finish -> FinishExperimentScreen(modifier = modifier)
        StepTrackingState.Setup -> StepTrackingSetupScreen(
            modifier = modifier,
            onStart = { fileName ->
                state = StepTrackingState.Running(fileName)
            }
        )
        is StepTrackingState.Running -> {
            val fileName = (state as StepTrackingState.Running).fileName
            val context = LocalContext.current
            val externalCacheFile = File(
                context.getExternalFilesDir(null),
                FileHelper.toCSVFileName(fileName, isTrackingStep = true)
            )
            val stream = FileOutputStream(externalCacheFile)
            stream.write(FileHelper.stepColumnNames.toByteArray())
            stream.write("\n".toByteArray())

            StepTrackingRunningScreen(
                modifier = modifier,
                onNewStepCounted = {
                    stream.write(
                        SimpleDateFormat(
                            "dd MMM yyyy HH:mm:ss:SSS Z",
                            Locale.getDefault()
                        ).format(Date()).toString().toByteArray()
                    )
                    stream.write("\n".toByteArray())
                },
                onFinish = {
                    stream.close()
                    state = StepTrackingState.Finish
                }
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewStepTracking() {
    IMUTransformerTheme {
        StepTrackingScreen(modifier = Modifier.fillMaxSize())
    }
}
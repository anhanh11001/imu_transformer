package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ducletran.tech.imutransformer.model.ExperimentStep
import ducletran.tech.imutransformer.ui.utils.SlotLayoutData
import ducletran.tech.imutransformer.ui.viewmodel.ExperimentViewModel
import ducletran.tech.imutransformer.utils.FileHelper
import java.io.File
import java.io.FileOutputStream

@Composable
fun ExperimentScreenWithNav(
    navController: NavController,
    experimentId: Long,
    onUpdateMainLayout: (SlotLayoutData) -> Unit
) {
    val experimentViewModel: ExperimentViewModel = viewModel(
        factory = ExperimentViewModel.Factory(experimentId)
    )

    val state = experimentViewModel.experimentStateFlow.collectAsState().value
    val formattedTopBarName = buildString {
        append(state.name)
        append(" (")
        append(state.currentStepIndex + 1)
        append("/")
        append(state.steps.size)
        append(")")
    }
    onUpdateMainLayout(SlotLayoutData(formattedTopBarName, true))
    when (val step = state.steps[state.currentStepIndex]) {
        ExperimentStep.Finish -> {
            FinishExperimentScreen(modifier = Modifier.fillMaxSize())
        }
        ExperimentStep.StepCounterInput -> {
            StepInputScreen(
                modifier = Modifier.fillMaxSize(),
                onStepCountConfirmed = {
                    experimentViewModel.confirmStepCount(it)
                }
            )
        }
        is ExperimentStep.Instruction -> {
            InstructionScreen(
                activityLabel = step.activityLabel,
                phoneStateLabel = step.phoneStateLabel,
                onStartClick = {
                    experimentViewModel.nextStep()
                }
            )
        }
        is ExperimentStep.Running -> {
            val context = LocalContext.current
            val externalCacheFile =
                File(context.getExternalFilesDir(null), FileHelper.randomFileName())
            val stream = FileOutputStream(externalCacheFile)
            stream.write(FileHelper.columnNames.toByteArray())
            stream.write("\n".toByteArray())

            SensorDataCollectionScreen(
                preparationTime = step.initialDelayTime,
                runningTime = step.runningTime,
                onRunFinished = {
                    stream.close()
                    experimentViewModel.nextStep()
                },
                onSensorDataCollected = {
                    val previousStep = state.steps[state.currentStepIndex - 1]
                    if (previousStep is ExperimentStep.Instruction) {
                        stream.write(
                            FileHelper.formatSensorData(
                                sensorData = it,
                                activityLabel = previousStep.activityLabel,
                                phoneStateLabel = previousStep.phoneStateLabel
                            ).toByteArray()
                        )
                        stream.write("\n".toByteArray())
                    }
                }
            )
        }
    }
}
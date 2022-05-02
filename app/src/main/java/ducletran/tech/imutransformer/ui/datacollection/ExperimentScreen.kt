package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ducletran.tech.imutransformer.model.ExperimentStep
import ducletran.tech.imutransformer.ui.viewmodel.ExperimentViewModel

@Composable
fun ExperimentScreenWithNav(
    navController: NavController,
    experimentId: Long
) {
    val experimentViewModel: ExperimentViewModel = viewModel(
        factory = ExperimentViewModel.Factory(experimentId)
    )

    val state = experimentViewModel.experimentStateFlow.collectAsState().value
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
            SensorDataCollectionScreen(
                preparationTime = step.initialDelayTime,
                runningTime = step.runningTime,
                onRunFinished = {
                    experimentViewModel.nextStep()
                }
            )
        }
    }
}
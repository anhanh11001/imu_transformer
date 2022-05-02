package ducletran.tech.imutransformer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ducletran.tech.imutransformer.model.DefaultExperimentSteps
import ducletran.tech.imutransformer.model.ExperimentStep
import kotlinx.coroutines.flow.MutableStateFlow

data class ExperimentState(
    val steps: List<ExperimentStep>,
    val currentStepIndex: Int,
    val currentRunningTime: Long
)

class ExperimentViewModel(
    private val experimentId: Long,
) : ViewModel() {

    val experimentStateFlow = MutableStateFlow(
        ExperimentState(
            steps = DefaultExperimentSteps.find(experimentId).steps,
            currentRunningTime = 0L,
            currentStepIndex = 0
        )
    )

    fun nextStep() {
        val stateValue = experimentStateFlow.value
        experimentStateFlow.value = stateValue.copy(
            currentStepIndex = stateValue.currentStepIndex + 1
        )
    }

    fun confirmStepCount(stepCounted: Int) {

    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val experimentId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ExperimentViewModel(experimentId) as T
        }

    }
}
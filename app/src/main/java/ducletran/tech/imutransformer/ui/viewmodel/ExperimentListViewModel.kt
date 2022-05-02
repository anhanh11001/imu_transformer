package ducletran.tech.imutransformer.ui.viewmodel

import androidx.lifecycle.ViewModel
import ducletran.tech.imutransformer.model.DefaultExperimentSteps
import kotlinx.coroutines.flow.MutableStateFlow

class ExperimentListViewModel : ViewModel() {

    val experimentsState = MutableStateFlow(
        listOf(
            DefaultExperimentSteps.Walking,
            DefaultExperimentSteps.Standing,
            DefaultExperimentSteps.Sitting
        )
    )
}
package ducletran.tech.imutransformer.model

sealed class ExperimentStep {

    data class Instruction(
        val activityLabel: Label,
        val phoneStateLabel: Label
    ) : ExperimentStep()

    object Finish : ExperimentStep()
    data class Running(
        val initialDelayTime: Long,
        val runningTime: Long
    ) : ExperimentStep()

    object StepCounterInput : ExperimentStep()
}

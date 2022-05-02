package ducletran.tech.imutransformer.model

import ducletran.tech.imutransformer.model.ExperimentStep.Instruction

val DefaultRunningTime = ExperimentStep.Running(
    initialDelayTime = 5_000L,
    runningTime = 20_000L
)


sealed class DefaultExperimentSteps(
    val id: Long,
    val description: String,
    val steps: List<ExperimentStep>
) {

    companion object {
        fun find(id: Long) = when (id) {
            1L -> Walking
            2L -> Sitting
            3L -> Standing
            else -> throw IllegalArgumentException("Cannot find experiment with id $id")
        }
    }

    object Walking : DefaultExperimentSteps(
        id = 1L,
        description = "Walking with phone",
        steps = listOf(
            Instruction(Label.Walking, Label.InsidePantPocket),
            DefaultRunningTime,
            Instruction(Label.Walking, Label.HoldingInHand),
            DefaultRunningTime,
            Instruction(Label.Walking, Label.InsideTheBag),
            DefaultRunningTime,
            Instruction(Label.Walking, Label.LyingOnDesk),
            DefaultRunningTime,
            Instruction(Label.Walking, Label.UsingInHand),
            ExperimentStep.Finish
        )
    )

    object Sitting : DefaultExperimentSteps(
        id = 2L,
        description = "Sitting with phone",
        steps = listOf(
            Instruction(Label.Sitting, Label.InsidePantPocket),
            DefaultRunningTime,
            Instruction(Label.Sitting, Label.HoldingInHand),
            DefaultRunningTime,
            Instruction(Label.Sitting, Label.InsideTheBag),
            DefaultRunningTime,
            Instruction(Label.Sitting, Label.LyingOnDesk),
            DefaultRunningTime,
            Instruction(Label.Sitting, Label.UsingInHand),
            ExperimentStep.Finish
        )
    )

    object Standing : DefaultExperimentSteps(
        id = 3L,
        description = "Standing with phone",
        steps = listOf(
            Instruction(Label.Standing, Label.InsidePantPocket),
            DefaultRunningTime,
            Instruction(Label.Standing, Label.HoldingInHand),
            DefaultRunningTime,
            Instruction(Label.Standing, Label.InsideTheBag),
            DefaultRunningTime,
            Instruction(Label.Standing, Label.LyingOnDesk),
            DefaultRunningTime,
            Instruction(Label.Standing, Label.UsingInHand),
            ExperimentStep.Finish
        )
    )
}
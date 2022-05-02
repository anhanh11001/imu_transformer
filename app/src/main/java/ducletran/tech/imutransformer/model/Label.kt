package ducletran.tech.imutransformer.model

sealed class Label(
    open val name: String,
    val type: LabelType
) {
    object Walking : Label("walking", LabelType.HUMAN_ACTIVITY)
    object Standing : Label("standing", LabelType.HUMAN_ACTIVITY)
    object Sitting : Label("sitting", LabelType.HUMAN_ACTIVITY)

    object LyingOnDesk : Label("lying on the desk", LabelType.PHONE_STATE)
    object InsideTheBag : Label("inside the bag", LabelType.PHONE_STATE)
    object InsidePantPocket : Label("inside the pant pocket", LabelType.PHONE_STATE)
    object HoldingInHand : Label("holding in hand", LabelType.PHONE_STATE)
    object UsingInHand : Label("being used in hand", LabelType.PHONE_STATE)

    data class Custom(override val name: String) : Label(name, LabelType.OTHERS)
}
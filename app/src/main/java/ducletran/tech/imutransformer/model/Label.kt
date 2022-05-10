package ducletran.tech.imutransformer.model

sealed class Label(
    open val name: String,
    val type: LabelType
) {
    object Walking : Label("walking", LabelType.HUMAN_ACTIVITY)
    object Standing : Label("standing", LabelType.HUMAN_ACTIVITY)
    object Sitting : Label("sitting", LabelType.HUMAN_ACTIVITY)

    object LyingOnDesk : Label("lyingonthedesk", LabelType.PHONE_STATE)
    object InsideTheBag : Label("insidethebag", LabelType.PHONE_STATE)
    object InsidePantPocket : Label("insidethepantpocket", LabelType.PHONE_STATE)
    object HoldingInHand : Label("holdinginhand", LabelType.PHONE_STATE)
    object UsingInHand : Label("beingusedinhand", LabelType.PHONE_STATE)

    data class Custom(override val name: String) : Label(name, LabelType.OTHERS)
}
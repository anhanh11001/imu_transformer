package ducletran.tech.imutransformer.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ducletran.tech.imutransformer.R

sealed class BottomTab(
    val route: String,
    @StringRes val descriptionId: Int,
    @DrawableRes val resourceId: Int
) {
    object Data : BottomTab("data", R.string.data, R.drawable.ic_run)
    object Label : BottomTab("label", R.string.label, R.drawable.ic_tag)
    object Model : BottomTab("model", R.string.model, R.drawable.ic_intelligence)

    companion object {
        val items = listOf(Data, Label, Model)
    }
}
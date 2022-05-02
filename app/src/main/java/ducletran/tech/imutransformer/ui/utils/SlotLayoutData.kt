package ducletran.tech.imutransformer.ui.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SlotLayoutData(
    val text: String,
    val isBackButtonEnabled: Boolean = false,
    val onFabSelected: (() -> Unit)? = null
) : Parcelable
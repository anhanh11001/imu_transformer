package ducletran.tech.imutransformer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ducletran.tech.imutransformer.data.database.entity.Label as EntityLabel
import ducletran.tech.imutransformer.data.database.dao.LabelDao
import ducletran.tech.imutransformer.model.Label
import ducletran.tech.imutransformer.model.LabelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel @Inject constructor(
    private val labelDao: LabelDao
) : ViewModel() {

    val labelListState = MutableStateFlow<List<Label>>(emptyList())

    private val predefinedLabelList = listOf(
        Label.Walking,
        Label.Sitting,
        Label.Standing,
        Label.LyingOnDesk,
        Label.UsingInHand,
        Label.InsidePantPocket,
        Label.InsideTheBag,
    )

    init {
        viewModelScope.launch {
            labelDao.getAll().collectLatest {
                val labels = it.map { Label.Custom(it.name) } + predefinedLabelList
                labelListState.value = labels
            }
        }
    }

    fun createLabel(labelName: String, labelType: LabelType) {
        viewModelScope.launch {
            labelDao.insertAll((EntityLabel(0, labelName, labelType.description)))
        }
    }
}
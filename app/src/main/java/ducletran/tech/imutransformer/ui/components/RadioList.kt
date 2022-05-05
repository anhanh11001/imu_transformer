package ducletran.tech.imutransformer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme


data class RadioRowData(
    val description: String,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

@Composable
fun RadioList(
    modifier: Modifier = Modifier,
    items: List<RadioRowData>
) {
    Column(modifier = modifier) {
        items.forEach {
            Row(
                modifier = Modifier.fillMaxWidth().clickable(onClick = it.onClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = it.isSelected, onClick = it.onClick)
                Text(text = it.description)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RadioListPreview() {
    IMUTransformerTheme {
        RadioList(
            items = listOf(
                RadioRowData("Adam", isSelected = true, onClick = {}),
                RadioRowData("Eve", isSelected = false, onClick = {}),
                RadioRowData("John", isSelected = false, onClick = {})
            )
        )
    }
}
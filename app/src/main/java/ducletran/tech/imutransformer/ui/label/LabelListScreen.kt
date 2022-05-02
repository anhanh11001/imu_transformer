package ducletran.tech.imutransformer.ui.label

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.model.Label
import ducletran.tech.imutransformer.ui.components.FullScreenText
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme

@Composable
fun LabelListScreen(
    modifier: Modifier = Modifier,
    labelList: List<Label>
) {
    if (labelList.isEmpty()) {
        FullScreenText(text = stringResource(id = R.string.empty_labels_description))
    } else {
        LazyColumn(modifier = modifier) {
            labelList
                .groupBy { it.type }
                .forEach { (type, labelList) ->
                    item("header_${type.description}") {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                            text = type.description,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(labelList, key = { it.name }) { label ->
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                            elevation = 4.dp
                        ) {
                            Text(
                                text = label.name.capitalize(Locale.current),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewLabelList() {
    IMUTransformerTheme {
        LabelListScreen(
            labelList = listOf(
                Label.Walking,
                Label.Sitting,
                Label.Standing,
                Label.LyingOnDesk,
                Label.UsingInHand,
                Label.InsidePantPocket,
                Label.InsideTheBag,
                Label.Custom("Jumping")
            )
        )
    }
}
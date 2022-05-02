package ducletran.tech.imutransformer.ui.datacollection

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import ducletran.tech.imutransformer.ui.theme.OrangeLight

data class Experiment(
    val name: String,
    val highlighted: Boolean
)

@Composable
fun ExperimentListScreen(
    modifier: Modifier = Modifier,
    experiments: List<Experiment>,
    onExperimentClick: (Experiment) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item("top_spacer") { Spacer(modifier = Modifier.height(16.dp)) }
        val (highlightedExperiments, normalExperiments) = experiments.partition { it.highlighted }

        items(highlightedExperiments, key = { it.name }) { exp ->
            ExperimentCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                experiment = exp,
                onClick = { onExperimentClick(exp) }
            )
        }

        items(normalExperiments, key = { it.name }) { exp ->
            ExperimentCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                experiment = exp,
                onClick = { onExperimentClick(exp) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExperimentCard(
    modifier: Modifier = Modifier,
    experiment: Experiment,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            if (experiment.highlighted) {
                Icon(
                    Icons.Rounded.Star,
                    null,
                    tint = OrangeLight
                )
                Spacer(Modifier.width(16.dp))
            }

            Text(text = experiment.name.capitalize(Locale.current))
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewExperimentList() {
    IMUTransformerTheme {
        ExperimentListScreen(
            experiments = listOf(
                Experiment("Walking Test", true),
                Experiment("All kind of stuff with phone location", false),
                Experiment("Standing Test", true),
                Experiment("Running Test", false),
                Experiment("Teaching Test", false)
            ),
            onExperimentClick = { }
        )
    }
}
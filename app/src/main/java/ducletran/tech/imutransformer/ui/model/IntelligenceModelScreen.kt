package ducletran.tech.imutransformer.ui.model

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.model.Label
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import ducletran.tech.imutransformer.ui.utils.RandomColorGenerator

@Composable
fun IntelligenceModelScreen(
    modifier: Modifier = Modifier,
    detectedLabels: Map<Label, Double>,
    isRunning: Boolean
) {
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        if (isRunning) {
            Text(text = stringResource(id = R.string.detecting), fontWeight = FontWeight.Bold)
            LabelPrediction(labelsWithAccuracy = detectedLabels)
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.ai_description)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { }) {
                Text(
                    text = if (isRunning) {
                        stringResource(id = R.string.stop)
                    } else {
                        stringResource(id = R.string.start)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LabelPrediction(
    modifier: Modifier = Modifier,
    labelsWithAccuracy: Map<Label, Double>
) {
    Column(modifier = modifier) {
        FlowRow {
            labelsWithAccuracy
                .keys
                .sortedByDescending { labelsWithAccuracy[it] }
                .forEachIndexed { index, label ->
                    val color by remember { mutableStateOf(RandomColorGenerator.get()) }
                    Chip(
                        enabled = true,
                        onClick = { },
                        colors = ChipDefaults.chipColors(backgroundColor = color)
                    ) {
                        Text(text = label.name)
                    }
                    if (index < labelsWithAccuracy.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
        }
        Text(
            text = stringResource(id = R.string.description),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyColumn {
            labelsWithAccuracy.forEach { (label, accuracy) ->
                item(label.name) {
                    LabelWithAccuracy(label = label, accuracy = accuracy)
                }
            }
        }
    }
}

@Composable
private fun LabelWithAccuracy(
    modifier: Modifier = Modifier,
    label: Label,
    accuracy: Double
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(bottom = 8.dp),
        elevation = 4.dp
    ) {
        val annotated = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(label.name)
            }
            append(": $accuracy%")
        }
        Text(text = annotated, modifier = Modifier.padding(16.dp))
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewRunning() {
    IMUTransformerTheme {
        IntelligenceModelScreen(
            isRunning = true,
            detectedLabels = mapOf(
                Label.Walking to 90.2,
                Label.HoldingInHand to 82.2
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewStopping() {
    IMUTransformerTheme {
        IntelligenceModelScreen(
            detectedLabels = emptyMap(),
            isRunning = false
        )

    }
}
package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.model.Label
import ducletran.tech.imutransformer.model.LabelType
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme

@Composable
fun InstructionScreen(
    modifier: Modifier = Modifier,
    activityLabel: Label,
    phoneStateLabel: Label,
    onStartClick: () -> Unit
) {
    require(activityLabel.type == LabelType.HUMAN_ACTIVITY)
    require(phoneStateLabel.type == LabelType.PHONE_STATE)

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val annotated: AnnotatedString = buildAnnotatedString {
            append(stringResource(id = R.string.pre_text_activity_description))
            append("\n\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                append(activityLabel.name.uppercase())
            }
            append("\n\n")
            append(stringResource(id = R.string.pre_text_phone_state_description))
            append("\n\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                append(phoneStateLabel.name.uppercase())
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = annotated,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onStartClick) {
                Text(text = stringResource(id = R.string.start))
            }
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewInstruction() {
    IMUTransformerTheme {
        InstructionScreen(
            activityLabel = Label.Walking,
            phoneStateLabel = Label.UsingInHand,
            onStartClick = { }
        )
    }
}
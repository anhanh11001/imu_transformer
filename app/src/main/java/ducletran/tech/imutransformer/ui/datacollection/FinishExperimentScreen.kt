package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ducletran.tech.imutransformer.ui.components.FullScreenText
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme

@Composable
fun FinishExperimentScreen(modifier: Modifier = Modifier) {
    FullScreenText(
        modifier = modifier,
        text = "This is the end of the experiment\uD83C\uDF89\uD83C\uDF89." +
                "\n\nThank you very much for participating in this experiment." +
                "\n\nThis input will be very valuable to my research."
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewFinishScreen() {
    IMUTransformerTheme {
        FinishExperimentScreen()
    }
}
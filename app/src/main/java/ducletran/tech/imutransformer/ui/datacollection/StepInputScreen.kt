package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ducletran.tech.imutransformer.R

@Composable
fun StepInputScreen(modifier: Modifier = Modifier) {
    var stepCounted by rememberSaveable { mutableStateOf(0) }
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.enter_step_count))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = stepCounted.toString(),
            onValueChange = { input ->
                input.toIntOrNull()?.let { stepCounted = it }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {

        }) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewStepInputScreen() {
    IMUTransformerTheme {
        StepInputScreen(modifier = Modifier.fillMaxSize())

    }
}
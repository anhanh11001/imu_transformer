package ducletran.tech.imutransformer.ui.label

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.model.LabelType
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme

@Composable
fun CreateLabelScreen(modifier: Modifier = Modifier) {
    var labelName by rememberSaveable { mutableStateOf("") }
    var selectedLabelType by remember { mutableStateOf(LabelType.PHONE_STATE) }
    Column(modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
            value = labelName,
            onValueChange = { labelName = it },
            placeholder = {
                Text(text = stringResource(id = R.string.enter_label_name))
            },
            label = {
                Text(text = stringResource(id = R.string.label_name))
            }
        )
        Text(text = stringResource(id = R.string.select_label_type))

        LabelType.values().forEach { labelType ->
            val onClick = { selectedLabelType = labelType }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = labelType == selectedLabelType,
                    onClick = onClick
                )
                Text(text = labelType.description)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewCreateLabel() {
    IMUTransformerTheme {
        CreateLabelScreen()
    }
}
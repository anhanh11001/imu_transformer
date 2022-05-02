package ducletran.tech.imutransformer.ui.datacollection

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import ducletran.tech.imutransformer.utils.HardwareSupport
import ducletran.tech.imutransformer.utils.tickerFlow
import kotlinx.coroutines.flow.collectLatest
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SensorDataCollectionScreen(
    modifier: Modifier = Modifier,
    preparationTime: Long,
    runningTime: Long,
    onRunFinished: () -> Unit
) {
    var currentTime by remember { mutableStateOf(0.00) }
    var vibrated by remember { mutableStateOf(false) }
    val isExperimentStarted = currentTime > preparationTime
    val formattedExperimentTime = if (isExperimentStarted) {
        val runningTimeInSeconds = runningTime / 1000
        val currentTimeInSeconds = round((currentTime - preparationTime) / 1000)
        "$currentTimeInSeconds/$runningTimeInSeconds"
    } else {
        val remainingTimeInSeconds = (preparationTime - currentTime) / 1000
        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.DOWN
        decimalFormat.format(remainingTimeInSeconds)
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (isExperimentStarted) {
                    stringResource(id = R.string.running_experiment)
                } else {
                    stringResource(id = R.string.experiment_starting)
                }
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = formattedExperimentTime,
                fontSize = 64.sp
            )
        }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = "time_flow", block = {
        val tickingTime = 100
        tickerFlow(tickingTime.milliseconds).collectLatest {
            if (currentTime > preparationTime + runningTime) {
                if (!vibrated) {
                    vibrated = true
                    HardwareSupport.oneShotVibrate(context)
                    onRunFinished()
                }
            } else {
                currentTime += tickingTime
            }
        }
    })
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SensorDataCollectionScreenPreview() {
    IMUTransformerTheme {
        SensorDataCollectionScreen(
            modifier = Modifier.fillMaxSize(),
            preparationTime = 2_000L,
            runningTime = 10_000L,
            onRunFinished = { }
        )
    }
}
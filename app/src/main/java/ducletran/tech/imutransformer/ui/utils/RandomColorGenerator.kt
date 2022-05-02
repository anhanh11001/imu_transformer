package ducletran.tech.imutransformer.ui.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object RandomColorGenerator {
    fun get() = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256),
        alpha = 200
    )
}
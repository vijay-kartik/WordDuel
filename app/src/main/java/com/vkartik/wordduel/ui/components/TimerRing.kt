package com.vkartik.wordduel.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkartik.wordduel.ui.theme.SpaceGrotesk
import com.vkartik.wordduel.ui.theme.WordDuelTheme
import kotlin.math.max

enum class TimerState {
    NORMAL,
    WARNING,
    CRITICAL
}

private val TimerTextStyle = TextStyle(
    fontFamily = SpaceGrotesk,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 24.sp
)

@Composable
fun TimerRing(
    secondsRemaining: Int,
    totalSeconds: Int,
    state: TimerState,
    modifier: Modifier = Modifier
) {
    val progressColor = when (state) {
        TimerState.NORMAL -> MaterialTheme.colorScheme.primary
        TimerState.WARNING -> MaterialTheme.colorScheme.secondary
        TimerState.CRITICAL -> MaterialTheme.colorScheme.error
    }
    
    val trackColor = MaterialTheme.colorScheme.outlineVariant

    // Calculate progress (1.0 to 0.0)
    val progress = if (totalSeconds > 0) {
        max(0f, secondsRemaining.toFloat() / totalSeconds.toFloat())
    } else 0f

    // Format time (e.g., "0:42", "0:07")
    val minutes = secondsRemaining / 60
    val seconds = secondsRemaining % 60
    val timeText = "$minutes:${seconds.toString().padStart(2, '0')}"

    Box(
        modifier = modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            trackColor = trackColor,
            strokeWidth = 6.dp, // Approximation based on 80dp size
        )
        Text(
            text = timeText,
            style = TimerTextStyle,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true, backgroundColor = 0xFF0B0D0E, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun TimerRingPreview() {
    WordDuelTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            TimerRing(
                secondsRemaining = 42,
                totalSeconds = 60,
                state = TimerState.NORMAL
            )
            Spacer(modifier = Modifier.width(16.dp))
            TimerRing(
                secondsRemaining = 18,
                totalSeconds = 60,
                state = TimerState.WARNING
            )
            Spacer(modifier = Modifier.width(16.dp))
            TimerRing(
                secondsRemaining = 7,
                totalSeconds = 60,
                state = TimerState.CRITICAL
            )
        }
    }
}

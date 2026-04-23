package com.vkartik.wordduel.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vkartik.wordduel.ui.theme.WordDuelTheme
import com.vkartik.wordduel.ui.theme.gameColors

enum class KeyState {
    DEFAULT,
    ABSENT,
    PRESENT,
    CORRECT
}

@Composable
fun GameKeyboard(
    onKeyClick: (Char) -> Unit,
    onEnterClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    keyStates: Map<Char, KeyState> = emptyMap(),
    modifier: Modifier = Modifier
) {
    val row1 = listOf('Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P')
    val row2 = listOf('A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L')
    val row3 = listOf('Z', 'X', 'C', 'V', 'B', 'N', 'M')

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row 1
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            row1.forEach { char ->
                KeyboardKey(
                    text = char.toString(),
                    state = keyStates[char] ?: KeyState.DEFAULT,
                    onClick = { onKeyClick(char) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Row 2
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Indent the second row
        ) {
            row2.forEach { char ->
                KeyboardKey(
                    text = char.toString(),
                    state = keyStates[char] ?: KeyState.DEFAULT,
                    onClick = { onKeyClick(char) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Row 3
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyboardKey(
                text = "ENTER",
                state = KeyState.DEFAULT,
                onClick = onEnterClick,
                modifier = Modifier.weight(1.5f)
            )
            row3.forEach { char ->
                KeyboardKey(
                    text = char.toString(),
                    state = keyStates[char] ?: KeyState.DEFAULT,
                    onClick = { onKeyClick(char) },
                    modifier = Modifier.weight(1f)
                )
            }
            KeyboardKey(
                text = "⌫",
                state = KeyState.DEFAULT,
                onClick = onBackspaceClick,
                modifier = Modifier.weight(1.5f)
            )
        }
    }
}

@Composable
fun KeyboardKey(
    text: String,
    state: KeyState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gameColors = MaterialTheme.gameColors

    val backgroundColor = when (state) {
        KeyState.DEFAULT -> MaterialTheme.colorScheme.surfaceContainerHigh
        KeyState.ABSENT -> gameColors.tileAbsentBackground
        KeyState.PRESENT -> gameColors.tilePresentBackground
        KeyState.CORRECT -> gameColors.tileCorrectBackground
    }

    val contentColor = when (state) {
        KeyState.DEFAULT -> MaterialTheme.colorScheme.onSurface
        KeyState.ABSENT -> gameColors.tileAbsentContent
        KeyState.PRESENT -> gameColors.tilePresentContent
        KeyState.CORRECT -> gameColors.tileCorrectContent
    }

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium, // Inter SemiBold 16sp
            color = contentColor
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true, backgroundColor = 0xFF0B0D0E, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun GameKeyboardPreview() {
    WordDuelTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            GameKeyboard(
                onKeyClick = {},
                onEnterClick = {},
                onBackspaceClick = {},
                keyStates = mapOf(
                    'A' to KeyState.CORRECT,
                    'B' to KeyState.ABSENT,
                    'C' to KeyState.PRESENT
                )
            )
        }
    }
}

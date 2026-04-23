package com.vkartik.wordduel.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vkartik.wordduel.ui.theme.TileLetterStyle
import com.vkartik.wordduel.ui.theme.WordDuelTheme
import com.vkartik.wordduel.ui.theme.gameColors

enum class TileState {
    EMPTY,
    ACTIVE,
    ABSENT,
    PRESENT,
    CORRECT
}

@Composable
fun LetterTile(
    letter: Char?,
    state: TileState,
    modifier: Modifier = Modifier
) {
    val gameColors = MaterialTheme.gameColors

    val backgroundColor = when (state) {
        TileState.EMPTY -> gameColors.tileEmptyBackground
        TileState.ACTIVE -> gameColors.tileActiveBackground
        TileState.ABSENT -> gameColors.tileAbsentBackground
        TileState.PRESENT -> gameColors.tilePresentBackground
        TileState.CORRECT -> gameColors.tileCorrectBackground
    }

    val borderColor = when (state) {
        TileState.EMPTY -> gameColors.tileEmptyBorder
        TileState.ACTIVE -> gameColors.tileActiveBorder
        else -> null
    }

    val contentColor = when (state) {
        TileState.ABSENT -> gameColors.tileAbsentContent
        TileState.PRESENT -> gameColors.tilePresentContent
        TileState.CORRECT -> gameColors.tileCorrectContent
        else -> MaterialTheme.colorScheme.onSurface
    }

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .size(56.dp)
            .clip(shape)
            .background(backgroundColor)
            .then(
                if (borderColor != null) {
                    Modifier.border(2.dp, borderColor, shape)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (letter != null) {
            Text(
                text = letter.uppercase(),
                style = TileLetterStyle,
                color = contentColor
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, backgroundColor = 0xFF0B0D0E, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun LetterTilePreview() {
    WordDuelTheme {
        Row {
            LetterTile(letter = null, state = TileState.EMPTY)
            Spacer(modifier = Modifier.width(8.dp))
            LetterTile(letter = 'A', state = TileState.ACTIVE)
            Spacer(modifier = Modifier.width(8.dp))
            LetterTile(letter = 'B', state = TileState.ABSENT)
            Spacer(modifier = Modifier.width(8.dp))
            LetterTile(letter = 'C', state = TileState.PRESENT)
            Spacer(modifier = Modifier.width(8.dp))
            LetterTile(letter = 'D', state = TileState.CORRECT)
        }
    }
}

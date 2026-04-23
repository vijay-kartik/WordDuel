package com.vkartik.wordduel.ui.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkartik.wordduel.ui.components.LetterTile
import com.vkartik.wordduel.ui.components.TileState
import com.vkartik.wordduel.ui.theme.SpaceGrotesk
import com.vkartik.wordduel.ui.theme.TileLetterStyle
import com.vkartik.wordduel.ui.theme.WordDuelTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    val splashTileStyle = TileLetterStyle.copy(fontSize = 26.sp)
    val tileSize = 52.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tile Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Row 1: W (Active), O (Present), R (Active)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    LetterTile(letter = 'W', state = TileState.ACTIVE, tileSize = tileSize, textStyle = splashTileStyle)
                    LetterTile(letter = 'O', state = TileState.PRESENT, tileSize = tileSize, textStyle = splashTileStyle)
                    LetterTile(letter = 'R', state = TileState.ACTIVE, tileSize = tileSize, textStyle = splashTileStyle)
                }
                // Row 2: D (Present), L (Absent), E (Present)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    LetterTile(letter = 'D', state = TileState.PRESENT, tileSize = tileSize, textStyle = splashTileStyle)
                    LetterTile(letter = 'L', state = TileState.ABSENT, tileSize = tileSize, textStyle = splashTileStyle)
                    LetterTile(letter = 'E', state = TileState.PRESENT, tileSize = tileSize, textStyle = splashTileStyle)
                }
                // Row 3: D (Active), U (Active), O (Absent)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    LetterTile(letter = 'D', state = TileState.ACTIVE, tileSize = tileSize, textStyle = splashTileStyle)
                    LetterTile(letter = 'U', state = TileState.ACTIVE, tileSize = tileSize, textStyle = splashTileStyle)
                    LetterTile(letter = 'O', state = TileState.ABSENT, tileSize = tileSize, textStyle = splashTileStyle)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Branding Text
            Text(
                text = "WordDuel",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Challenge your friends",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, backgroundColor = 0xFF0B0D0E, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun SplashScreenPreview() {
    WordDuelTheme {
        SplashScreen()
    }
}

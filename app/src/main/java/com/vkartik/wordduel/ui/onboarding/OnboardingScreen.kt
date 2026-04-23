package com.vkartik.wordduel.ui.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkartik.wordduel.ui.components.LetterTile
import com.vkartik.wordduel.ui.components.TileState
import com.vkartik.wordduel.ui.components.WordDuelButton
import com.vkartik.wordduel.ui.theme.Inter
import com.vkartik.wordduel.ui.theme.TileLetterStyle
import com.vkartik.wordduel.ui.theme.WordDuelTheme
import com.vkartik.wordduel.ui.theme.gameColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    initialPage: Int = 0
) {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> OnboardingPage(
                    title = "Guess the Word",
                    subtitle = "Classic 5-letter word guessing with\n6 tries. Green, yellow, and gray\ntiles guide your way.",
                    visual = { Page1Visual() }
                )
                1 -> OnboardingPage(
                    title = "Create Rooms",
                    subtitle = "Play with friends in private rooms.\nTrack scores, climb leaderboards,\nand settle who's the best.",
                    visual = { Page2Visual() }
                )
                2 -> OnboardingPage(
                    title = "Challenge Friends",
                    subtitle = "Curate your own word list and\nchallenge friends to guess them.\nWho knows words best?",
                    visual = { Page3Visual() }
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Pager Indicator
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(if (isSelected) 24.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Actions
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            if (pagerState.currentPage == 2) {
                WordDuelButton(
                    text = "GET STARTED",
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = "Next",
                            fontFamily = Inter,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Skip",
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable { onFinish() }
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    title: String,
    subtitle: String,
    visual: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Fixed height box for the visual to prevent jumping between pages
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            visual()
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = Inter,
                fontSize = 15.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Page1Visual() {
    val style = TileLetterStyle.copy(fontSize = 22.sp)
    val size = 44.dp
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        LetterTile(letter = 'H', state = TileState.ACTIVE, tileSize = size, textStyle = style)
        LetterTile(letter = 'E', state = TileState.PRESENT, tileSize = size, textStyle = style)
        LetterTile(letter = 'L', state = TileState.ABSENT, tileSize = size, textStyle = style)
        LetterTile(letter = 'L', state = TileState.ACTIVE, tileSize = size, textStyle = style)
        LetterTile(letter = 'O', state = TileState.ACTIVE, tileSize = size, textStyle = style)
    }
}

@Composable
private fun Page2Visual() {
    val gameColors = MaterialTheme.gameColors
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gameColors.tileActiveBackground.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(gameColors.tileActiveBackground))
            Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(gameColors.tileActiveBackground))
            Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(gameColors.tileActiveBackground))
        }
    }
}

@Composable
private fun Page3Visual() {
    val gameColors = MaterialTheme.gameColors
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gameColors.tilePresentBackground.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "VS",
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = gameColors.tilePresentBackground
        )
    }
}

@Preview(name = "Page 1 - Dark Mode", showBackground = true, backgroundColor = 0xFF0B0D0E, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Page 1 - Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun OnboardingScreenPreview() {
    WordDuelTheme {
        OnboardingScreen(onFinish = {})
    }
}

@Preview(name = "Page 2 - Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun OnboardingScreenPage2Preview() {
    WordDuelTheme {
        OnboardingScreen(onFinish = {}, initialPage = 1)
    }
}

@Preview(name = "Page 3 - Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun OnboardingScreenPage3Preview() {
    WordDuelTheme {
        OnboardingScreen(onFinish = {}, initialPage = 2)
    }
}

package com.vkartik.wordduel.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkartik.wordduel.ui.theme.SpaceGrotesk
import com.vkartik.wordduel.ui.theme.WordDuelTheme

// Specific typography style for the CTA button based on Figma
private val ButtonCtaStyle = TextStyle(
    fontFamily = SpaceGrotesk,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    letterSpacing = 1.2.sp // 1.2px tracking from Figma
)

@Composable
fun WordDuelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = ButtonCtaStyle
        )
    }
}

@Composable
fun WordDuelOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(28.dp),
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = ButtonCtaStyle
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true, backgroundColor = 0xFF0B0D0E, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, backgroundColor = 0xFFF5F4EF)
@Composable
private fun WordDuelButtonPreview() {
    WordDuelTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            WordDuelButton(
                text = "ENTER THE ARENA",
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            WordDuelOutlinedButton(
                text = "SECONDARY ACTION",
                onClick = {}
            )
        }
    }
}

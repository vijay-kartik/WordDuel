package com.vkartik.wordduel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vkartik.wordduel.ui.navigation.WordDuelNavGraph
import com.vkartik.wordduel.ui.theme.WordDuelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordDuelTheme {
                WordDuelNavGraph()
            }
        }
    }
}

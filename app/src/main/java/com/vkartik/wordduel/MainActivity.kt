package com.vkartik.wordduel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.vkartik.wordduel.ui.theme.WordDuelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordDuelTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                    Text("Hello Android")
                }
            }
        }
    }
}

package com.kirilpetriv.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kirilpetriv.shopping.feature.ShoppingScreen
import com.kirilpetriv.shopping.ui.theme.ShoppingTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingTheme {
                KoinContext {
                    ShoppingScreen()
                }
            }
        }
    }
}
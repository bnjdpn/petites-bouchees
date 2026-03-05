package com.giulia.diversification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giulia.diversification.ui.navigation.AppNavigation
import com.giulia.diversification.ui.theme.GiuliaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GiuliaTheme {
                AppNavigation()
            }
        }
    }
}

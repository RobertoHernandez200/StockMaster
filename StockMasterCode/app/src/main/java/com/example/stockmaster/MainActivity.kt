package com.example.stockmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.stockmaster.ui.navigation.NavGraph
import com.example.stockmaster.ui.theme.StockMasterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StockMasterTheme {
                NavGraph()
            }
        }
    }
}
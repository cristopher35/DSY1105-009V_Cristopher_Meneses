package com.example.cimucm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cimucm.ui.MainScreen
import com.example.cimucm.ui.theme.CIMUCMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CIMUCMTheme {
                MainScreen()
            }
        }
    }
}
package com.triangle.n12medic.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.triangle.n12medic.ui.theme.N12MedicTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("authScreen"),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}
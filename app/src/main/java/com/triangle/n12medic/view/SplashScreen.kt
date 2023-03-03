package com.triangle.n12medic.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.theme.N12MedicTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    // Класс SplashScreen
    // Дата создания: 27.02.2023 12:00
    // Автор: Triangle

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            val mContext = LocalContext.current

            val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)
            val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
            val token = sharedPreferences.getString("token", "")
            val passwordSkipped = sharedPreferences.getBoolean("passwordSkipped", true)

            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SplashScreenContent()
                    scope.launch {
                        delay(2000)

                        if (isFirstLaunch) {
                            val intent = Intent(mContext, OnboardActivity::class.java)
                            startActivity(intent)
                        } else {
                            if (token != null) {
                                if (passwordSkipped) {
                                    val intent = Intent(mContext, CreatePasswordActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(mContext, OrderActivity::class.java) //Todo
                                    startActivity(intent)
                                }
                            } else {
                                val intent = Intent(mContext, AuthActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SplashScreenContent() { // Контент SplashScreen
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_bg),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = ""
            )
        }
    }
}
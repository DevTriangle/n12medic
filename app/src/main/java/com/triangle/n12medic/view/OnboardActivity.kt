package com.triangle.n12medic.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.components.AppTextButton
import com.triangle.n12medic.ui.components.DotsIndicator
import com.triangle.n12medic.ui.theme.N12MedicTheme

class OnboardActivity : ComponentActivity() {

    companion object {
        const val IS_FIRST_LAUNCH_KEY = "isFirstLaunch"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnboradScreenContent()
                }
            }


            BackHandler() {}
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun OnboradScreenContent() {
        val mContext = LocalContext.current

        val screenTitles = listOf("Анализы", "Уведомления", "Мониторинг")
        val screenDescriptions = listOf("Экспресс сбор и получение проб", "Вы быстро узнаете о результатах", "Наши врачи всегда наблюдают за вашими показателями здоровья")
        val screenImages = listOf(R.drawable.splash_1, R.drawable.splash_2, R.drawable.splash_3)

        var buttonLabel by rememberSaveable { mutableStateOf("") }

        val pagerState = rememberPagerState()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect() {
                buttonLabel = if (it == screenTitles.size - 1) {
                    "Завершить"
                } else {
                    "Пропустить"
                }
            }
        }

        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppTextButton(
                    modifier = Modifier
                        .padding(horizontal = 30.dp),
                    label = buttonLabel,
                    onClick = {
                        val intent = Intent(mContext, AuthActivity::class.java)
                        startActivity(intent)

                        saveFirstLaunchTag()
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.splash_plus_shape),
                    contentDescription = "",
                )
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxHeight()
                    .testTag("pager"),
                count = 3,
                state = pagerState
            ) { index ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 60.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .testTag("title"),
                                text = screenTitles[index],
                                fontSize = 20.sp,
                                color = Color(0xFF00B712),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(29.dp))
                            Text(
                                text = screenDescriptions[index],
                                fontSize = 14.sp,
                                color = Color(0xFF939396),
                                textAlign = TextAlign.Center
                            )
                        }
                        DotsIndicator(
                            count = screenTitles.size,
                            currentIndex = index
                        )
                        Image(
                            painter = painterResource(id = screenImages[index]),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }

    private fun saveFirstLaunchTag() {
        val sharedPreferencesName = "shared"

        val sharedPreferences = this.getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putBoolean(IS_FIRST_LAUNCH_KEY, false)
            apply()
        }
    }
}
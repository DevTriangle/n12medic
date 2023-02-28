package com.triangle.n12medic.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.ui.components.AppTextButton
import com.triangle.n12medic.ui.components.PasswordDotsIndicator
import com.triangle.n12medic.ui.components.PasswordKeyButton
import com.triangle.n12medic.ui.theme.N12MedicTheme

class CreatePasswordActivity : ComponentActivity() {
    // Класс для создания пароля приложения
    // Дата создания: 28.02.2023 13:09
    // Автор: Triangle
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreatePasswordContent()
                }
            }
        }
    }
    
    @Composable
    fun CreatePasswordContent() {
        var passwordCode by rememberSaveable { mutableStateOf("") }
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    AppTextButton(
                        label = "Пропустить",
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.Transparent
                        ),
                        onClick = {  },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 17.sp,
                            color = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        ) {
            Box(Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Создайте пароль",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Для защиты ваших персональных данных",
                            fontSize = 15.sp,
                            color = Color(0xFF939396),
                            textAlign = TextAlign.Center
                        )
                    }
                    PasswordDotsIndicator(
                        count = 4,
                        currentCount = passwordCode.length
                    )
                    PasswordKeyboard(onCodeChange = {
                        passwordCode = it
                    })
                }
            }
        }
    }

    @Composable
    fun PasswordKeyboard(
        onCodeChange: (String) -> Unit
    ) {
        var code: String = ""

        LazyVerticalGrid(
            modifier = Modifier
                .width(312.dp),
            columns = GridCells.Fixed(3),
            content = {
                items(count = 9) { index ->
                    PasswordKeyButton(
                        number = index + 1,
                        onClick = {
                            if (code.length < 4) {
                                code = "${code}$it"
                                onCodeChange(code)
                            }
                        }
                    )
                }
                item() {

                }
                item() {
                    PasswordKeyButton(
                        number = 0,
                        onClick = {
                            if (code.length < 4) {
                                code = "${code}$it"
                                onCodeChange(code)
                            }
                        }
                    )
                }
                item() {

                }
            }
        )
    }
}
package com.triangle.n12medic.view

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.Layout.Alignment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic.ui.components.AppButton
import com.triangle.n12medic.ui.components.AppTextField
import com.triangle.n12medic.ui.theme.N12MedicTheme
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.components.AppOutlinedButton
import com.triangle.n12medic.viewmodel.AuthViewModel

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
                    AuthScreenContent()
                }
            }
        }
    }

    @Composable
    fun AuthScreenContent() { // Содержание экрана авторизации
        val mContext = LocalContext.current
        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        var emailValue by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var emailError by rememberSaveable { mutableStateOf("") }

        val message by viewModel.message.observeAsState()
        LaunchedEffect(message) {
            if (message == "Успешно код отправлен") {
                val intent = Intent(mContext, EmailCodeActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            }
        }
        val errorMessage by viewModel.errorMessage.observeAsState()
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Spacer(modifier = Modifier.height(50.dp))
                ScreenTitle()
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Вход по E-mail",
                    fontSize = 14.sp,
                    color = Color(0xFF7E7E9A)
                )
                Spacer(modifier = Modifier.height(5.dp))
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = emailValue,
                    onValueChange = {
                        emailValue = it
                    },
                    error = emailError,
                    label = "example@mail.ru"
                )
                Spacer(modifier = Modifier.height(32.dp))
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Далее",
                    enabled = emailValue.trim() != "",
                    onClick = {
                        if (Regex("^[a-z0-9]+@([a-z0-9.]+)+[a-z]{2,}$").matches(emailValue)) {
                            email = emailValue
                            viewModel.sendCode(emailValue)
                        } else {
                            emailError = "Неверный формат Email"
                        }
                    }
                )
            }
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Или войдите с помощью",
                    fontSize = 15.sp,
                    color = Color(0xFF939396)
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppOutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Войти с Яндекс",
                    onClick = {
                        
                    }
                )
            }
        }
    }

    @Composable
    fun ScreenTitle() { // Заголовок экрана
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row() {
                Image(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.ic_hello),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Добро пожаловать!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(23.dp))
            Text(
                text = "Войдите, чтобы пользоваться функциями приложения",
                fontSize = 15.sp
            )
        }
    }
}
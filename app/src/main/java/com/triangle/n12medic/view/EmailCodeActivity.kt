package com.triangle.n12medic.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic.ui.components.AppIconButton
import com.triangle.n12medic.ui.theme.N12MedicTheme
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.components.AppTextButton
import com.triangle.n12medic.ui.components.AppTextField
import com.triangle.n12medic.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

class EmailCodeActivity : ComponentActivity() {
    // Класс для ввода кода при авторизации через Email
    // Дата создания: 28.02.2023 12:31
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EmailCodeContent()
                }
            }
        }
    }

    @Composable
    fun EmailCodeContent() {
        val mContext = LocalContext.current
        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)

        val email = intent.getStringExtra("email")

        var time by rememberSaveable { mutableStateOf(60) }
        val focus = LocalFocusManager.current

        var code1 by rememberSaveable { mutableStateOf("") }
        var code2 by rememberSaveable { mutableStateOf("") }
        var code3 by rememberSaveable { mutableStateOf("") }
        var code4 by rememberSaveable { mutableStateOf("") }

        var isErrorVisible by rememberSaveable { mutableStateOf(false) }

        val token by viewModel.authToken.observeAsState()
        LaunchedEffect(token) {
            if (token != null) {
                val intent = Intent(mContext, CreatePasswordActivity::class.java)
                startActivity(intent)

                with(sharedPreferences.edit()) {
                    putString("token", token)
                    apply()
                }
            }
        }

        val errorMessage by viewModel.authErrorMessage.observeAsState()
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                isErrorVisible = true
            }
        }

        LaunchedEffect(time) {
            if (time > 0) {
                delay(1000)
                time--
            } else {
                viewModel.sendCode(email!!)
                time = 60
            }
        }

        Scaffold(
            topBar = {
                AppIconButton(
                    modifier = Modifier
                        .padding(20.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    onClick = {
                        onBackPressed()
                    }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Введите код из E-mail",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AppTextField(
                            modifier = Modifier
                                .size(48.dp),
                            value = code1,
                            onValueChange = { value ->
                                if (value.isDigitsOnly()) {
                                    if (value == "") {
                                        code1 = value
                                    } else if (value.length <= 1) {
                                        code1 = value
                                        focus.moveFocus(FocusDirection.Next)
                                    }
                                }

                            },
                            contentPadding = PaddingValues(5.dp),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            ),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            modifier = Modifier
                                .size(48.dp),
                            value = code2,
                            onValueChange = { value ->
                                if (value.isDigitsOnly()) {
                                    if (value == "") {
                                        code2 = value
                                        focus.moveFocus(FocusDirection.Previous)
                                    } else if (value.length <= 1) {
                                        code2 = value
                                        focus.moveFocus(FocusDirection.Next)
                                    }
                                }
                            },
                            contentPadding = PaddingValues(5.dp),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            modifier = Modifier
                                .size(48.dp),
                            value = code3,
                            onValueChange = { value ->
                                if (value.isDigitsOnly()) {
                                    if (value == "") {
                                        code3 = value
                                        focus.moveFocus(FocusDirection.Previous)
                                    } else if (value.length <= 1) {
                                        code3 = value
                                        focus.moveFocus(FocusDirection.Next)
                                    }
                                }

                            },
                            contentPadding = PaddingValues(5.dp),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            ),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            modifier = Modifier
                                .size(48.dp),
                            value = code4,
                            onValueChange = { value ->
                                if (value.isDigitsOnly()) {
                                    if (value == "") {
                                        code4 = value
                                        focus.moveFocus(FocusDirection.Previous)
                                    } else if (value.length <= 1) {
                                        code4 = value
                                        focus.moveFocus(FocusDirection.Next)

                                        if (email != null) {
                                            viewModel.authUser(email, "${code1}${code2}${code3}${code4}")
                                        }
                                    }
                                }

                            },
                            contentPadding = PaddingValues(5.dp),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            ),
                            singleLine = true
                        )
                    }
                    AnimatedVisibility(visible = errorMessage != null) {
                        Column() {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = errorMessage.toString(),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.error
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Отправить код повторно можно будет через $time секунд",
                        fontSize = 15.sp,
                        color = Color(0xFF939396)
                    )
                }
            }
        }

        if (isErrorVisible) {
            AlertDialog(
                onDismissRequest = { isErrorVisible = false },
                title = {
                    Text(
                        text = "Ошибка",
                        fontSize = 18.sp
                    )
                },
                text = {
                    errorMessage?.let { Text(text = it) }
                },
                buttons = {
                    AppTextButton(
                        label = "Ок",
                        onClick = {
                            isErrorVisible = false
                        }
                    )
                }
            )
        }
    }
}
package com.triangle.n12medic.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat.IntentBuilder
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic.ui.components.AppTextButton
import com.triangle.n12medic.ui.components.AppTextField
import com.triangle.n12medic.ui.theme.N12MedicTheme
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.components.AppButton
import com.triangle.n12medic.viewmodel.CardManageViewModel

class CreateCardActivity : ComponentActivity() {
    // Создание карточки пациента
    // Дата создания: 28.02.2023 15:00
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateCardContent()
                }
            }
        }
    }

    @Composable
    fun CreateCardContent() {
        val mContext = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = "Создание карты пациента",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                AppTextButton(
                    modifier = Modifier
                        .weight(4f),
                    label = "Пропустить",
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.primary,
                        backgroundColor = Color.Transparent
                    ),
                    onClick = {
                        val intent = Intent(mContext,HomeActivity::class.java)
                        startActivity(intent)
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 17.sp,
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Без карты пациента вы не сможете заказать анализы.",
                fontSize = 15.sp,
                color = Color(0xFF939396),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
                fontSize = 15.sp,
                color = Color(0xFF939396),
            )
            Spacer(modifier = Modifier.height(32.dp))
            CardForm()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CardForm() {
        val mContext = LocalContext.current
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)
        val viewModel = ViewModelProvider(this)[CardManageViewModel::class.java]

        var firstName by rememberSaveable { mutableStateOf("") }
        var patronymic by rememberSaveable { mutableStateOf("") }
        var lastName by rememberSaveable { mutableStateOf("") }
        var birthday by rememberSaveable { mutableStateOf("") }
        var gender by rememberSaveable { mutableStateOf("") }

        var genderExpanded by rememberSaveable { mutableStateOf(false) }
        val token = sharedPreferences.getString("token", "")

        val isSuccess by viewModel.isSuccess.observeAsState()
        LaunchedEffect(isSuccess) {
            if (isSuccess != null) {
                val intent = Intent(mContext,HomeActivity::class.java)
                startActivity(intent)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = firstName,
                placeholder = { Text("Имя") },
                onValueChange = {
                    firstName = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = patronymic,
                placeholder = { Text("Отчество") },
                onValueChange = {
                    patronymic = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = lastName,
                placeholder = { Text("Фамилия") },
                onValueChange = {
                    lastName = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = birthday,
                placeholder = { Text("Дата рождения") },
                onValueChange = {
                    birthday = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = genderExpanded,
                onExpandedChange = {
                    genderExpanded = it
                }
            ) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = gender,
                    placeholder = { Text("Пол") },
                    readOnly = true,
                    onValueChange = {
                        gender = it
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_down),
                            contentDescription = "",
                            tint = Color(0xFF7E7E9A)
                        )
                        DropdownMenu(
                            expanded = genderExpanded,
                            onDismissRequest = { genderExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    gender = "Не выбран"
                                    genderExpanded = false
                                }
                            ) {
                                Text(text = "Не выбран")
                            }
                            DropdownMenuItem(
                                onClick = {
                                    gender = "Мужской"
                                    genderExpanded = false
                                }
                            ) {
                                Text(text = "Мужской")
                            }
                            DropdownMenuItem(
                                onClick = {
                                    gender = "Женский"
                                    genderExpanded = false
                                }
                            ) {
                                Text(text = "Женский")
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Создать",
                onClick = {
                    if (token != null) {
                        viewModel.createCard(
                            name = firstName,
                            lastName = lastName,
                            patronymic = patronymic,
                            bith = birthday,
                            pol = gender,
                            token = token
                        )
                    }
                }
            )
        }
    }
}
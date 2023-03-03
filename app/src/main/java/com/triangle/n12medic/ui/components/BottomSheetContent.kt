package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.model.Analysis
import com.triangle.n12medic.R
import kotlinx.coroutines.launch

// Содержание экрана формирования адреса сдачи анализов
@Composable
fun AddressBottomSheetContent(
    onMapIconClick: () -> Unit
) {
    var address by rememberSaveable { mutableStateOf("") }

    var long by rememberSaveable { mutableStateOf("") }
    var lat by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    var flatNum by rememberSaveable { mutableStateOf("") }
    var entrance by rememberSaveable { mutableStateOf("") }
    var floor by rememberSaveable { mutableStateOf("") }
    var doorphone by rememberSaveable { mutableStateOf("") }

    var saveAddress by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text(
                "Адрес сдачи анализов",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
            )
            AppIconButton(
                painter = painterResource(id = R.drawable.ic_map),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color(0xFFB8C1CC)
                ),
                onClick = onMapIconClick
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = address,
            onValueChange = { address = it },
            label = {
                Text(
                    text = "Ваш адрес",
                    fontSize = 14.sp,
                    color = Color(0xFF7E7E9A)
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .padding(end = 12.dp),
                value = long,
                onValueChange = { long = it },
                label = {
                    Text(
                        text = "Долгота",
                        fontSize = 14.sp,
                        color = Color(0xFF7E7E9A)
                    )
                },
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(end = 12.dp),
                value = lat,
                onValueChange = { lat = it },
                label = {
                    Text(
                        text = "Широта",
                        fontSize = 14.sp,
                        color = Color(0xFF7E7E9A)
                    )
                }
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(1f),
                value = height,
                onValueChange = { height = it },
                label = {
                    Text(
                        text = "Высота",
                        fontSize = 14.sp,
                        color = Color(0xFF7E7E9A)
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .padding(end = 12.dp),
                value = flatNum,
                onValueChange = { flatNum = it },
                label = {
                    Text(
                        text = "Квартира",
                        fontSize = 14.sp,
                        color = Color(0xFF7E7E9A)
                    )
                },
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.66f)
                    .padding(end = 12.dp),
                value = entrance,
                onValueChange = { entrance = it },
                label = {
                    Text(
                        text = "Подъезд",
                        fontSize = 14.sp,
                        color = Color(0xFF7E7E9A)
                    )
                }
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = floor,
                onValueChange = { floor = it },
                label = {
                    Text(
                        text = "Этаж",
                        fontSize = 14.sp,
                        color = Color(0xFF7E7E9A)
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = doorphone,
            onValueChange = { doorphone = it },
            label = {
                Text(
                    text = "Домофон",
                    fontSize = 14.sp,
                    color = Color(0xFF7E7E9A)
                )
            }
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Сохранить этот адрес?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = saveAddress,
                onCheckedChange = {
                    saveAddress = it
                },
                colors = SwitchDefaults.colors(
                    uncheckedTrackColor = Color(0xFFEBEBEB),
                    uncheckedTrackAlpha = 1f,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colors.primary,
                    checkedTrackAlpha = 1f,
                    checkedThumbColor = Color.White
                )
            )
        }
    }
}
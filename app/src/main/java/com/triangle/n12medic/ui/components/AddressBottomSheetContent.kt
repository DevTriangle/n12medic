package com.triangle.n12medic.ui.components

import android.location.Address
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
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
import com.triangle.n12medic.R

// Содержание экрана формирования адреса сдачи анализов
@Composable
fun AddressBottomSheetContent(
    onMapIconClick: () -> Unit,
    onAddressSelect: (String) -> Unit,
    lat: String,
    lon: String,
    alt: String,
    address: String
) {
    //var address by rememberSaveable { mutableStateOf("") }

//    var long by rememberSaveable { mutableStateOf("") }
//    var lat by rememberSaveable { mutableStateOf("") }
//    var height by rememberSaveable { mutableStateOf("") }

    var flatNum by rememberSaveable { mutableStateOf("") }
    var entrance by rememberSaveable { mutableStateOf("") }
    var floor by rememberSaveable { mutableStateOf("") }
    var doorphone by rememberSaveable { mutableStateOf("") }
    var addressName by rememberSaveable { mutableStateOf("") }

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
                .padding(vertical = 4.dp)
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
            onValueChange = {
                // address = it
                            },
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
                value = lon,
                onValueChange = {  },
                readOnly = true,
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
                onValueChange = {  },
                readOnly = true,
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
                value = alt,
                onValueChange = {  },
                readOnly = true,
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Сохранить этот адрес?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            androidx.compose.material3.Switch(
                checked = saveAddress,
                onCheckedChange = {
                    saveAddress = it
                },
                colors = androidx.compose.material3.SwitchDefaults.colors(
                    uncheckedTrackColor = Color(0xFFEBEBEB),
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colors.primary,
                    checkedThumbColor = Color.White,
                    checkedBorderColor = MaterialTheme.colors.primary,
                    uncheckedBorderColor = Color(0xFFEBEBEB)
                )
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        AnimatedVisibility(visible = saveAddress) {
            Column() {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = addressName,
                    onValueChange = { addressName = it },
                    label = {
                        Text(
                            text = "Название: например дом, работа",
                            fontSize = 14.sp,
                            color = Color(0xFF939396)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Подтвердить",
            onClick = {
                onAddressSelect(address)
            }
        )
    }
}
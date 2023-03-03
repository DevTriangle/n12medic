package com.triangle.n12medic.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.model.CartItem
import com.triangle.n12medic.model.Patient

// Карточка с отображением информации о пациенте в меню выбора пациента при оформлении заказа
@Composable
fun PatientCard(
    modifier: Modifier = Modifier,
    patient: Patient,
    selected: Boolean = false,
    onSelect: (Patient) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = { onSelect(patient) }
            ),
        elevation = 0.dp,
        backgroundColor = if (selected) MaterialTheme.colors.primary else Color(0xFFF5F5F9)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (patient.pol == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${patient.lastName} ${patient.firstName}",
                fontSize = 16.sp,
                color = if (selected) Color.White else Color.Black
            )
        }
    }
}

// Карточка с отображением информации о пациенте в оформлении заказа с выбором  услуг
@Composable
fun OrderPatientCard(
    modifier: Modifier = Modifier,
    patient: Patient,
    cart: MutableList<CartItem>,
    interactionSource: MutableInteractionSource,
    onRemoveClick: () -> Unit,
    onPatientCartChange: (MutableList<CartItem>) -> Unit
) {
    val tCart: MutableList<CartItem> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        tCart.addAll(cart)
    }

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, Color(0xFFEBEBEB), MaterialTheme.shapes.medium),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    value = "${patient.lastName} ${patient.firstName}",
                    onValueChange = {},
                    readOnly = true,
                    interactionSource = interactionSource,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_down),
                            contentDescription = "",
                            tint = Color(0xFF7E7E9A)
                        )
                    },
                    leadingIcon = {
                        Image(
                            painter = if (patient.pol == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(
                                id = R.drawable.ic_female
                            ),
                            contentDescription = ""
                        )
                    }
                )
                IconButton(
                    onClick = onRemoveClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        tint = Color(0xFFB8C1CC)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                .fillMaxWidth()
            ) {
                for (item in cart) {
                    var isChecked by remember { mutableStateOf(true) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppCheckbox(
                                modifier = Modifier
                                    .size(20.dp),
                                checked = isChecked,
                                onCheckedChange = {
                                    if (it) tCart.add(item)
                                    else tCart.remove(item)

                                    onPatientCartChange(tCart)

                                    isChecked = it
                                }
                            )
                            Text(
                                text = item.name,
                                color = if (isChecked) Color.Black else Color(0xFF939396),
                                fontSize = 12.sp
                            )
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(0.4f),
                            text = "${item.price} ₽",
                            color = if (isChecked) Color.Black else Color(0xFF939396),
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
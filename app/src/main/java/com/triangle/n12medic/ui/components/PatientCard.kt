package com.triangle.n12medic.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
    onRemoveClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, Color(0xFFEBEBEB), MaterialTheme.shapes.medium),
        elevation = 0.dp,
    ) {
        Row() {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                value = "${patient.lastName} ${patient.firstName}",
                onValueChange = {},
                readOnly = true,
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
    }
}
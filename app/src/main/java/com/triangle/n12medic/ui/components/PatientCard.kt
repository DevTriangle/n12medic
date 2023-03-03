package com.triangle.n12medic.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.model.Patient

// Карточка с отображением информации о пациенте в меню выбора пациента при оформлении заказа
@Composable
fun PatientCard(
    modifier: Modifier = Modifier,
    patient: Patient,
    selected: Boolean = false
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = if (selected) MaterialTheme.colors.primary else Color(0xFFF5F5F9)
    ) {
        Row() {
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
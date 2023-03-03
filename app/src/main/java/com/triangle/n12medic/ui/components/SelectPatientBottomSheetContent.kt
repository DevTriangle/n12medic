package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.model.Patient

@Composable
fun SelectPatientBottomSheetContent(
    patientList: MutableList<Patient>,
    onCloseClick: () -> Unit
) {
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
                painter = painterResource(id = R.drawable.ic_close),
                shape = CircleShape,
                onClick = onCloseClick
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}
package com.triangle.n12medic.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.model.Patient
import com.triangle.n12medic.view.CreateCardActivity
import kotlinx.coroutines.launch

@Composable
fun SelectPatientBottomSheetContent(
    patientList: MutableList<Patient>,
    onCloseClick: () -> Unit,
    onSelectClick: (Patient) -> Unit,
) {
    val mContext = LocalContext.current
    var selectedPatient: Patient? by remember { mutableStateOf(null) }
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
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Выбор пациента",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
            )
            AppIconButton(
                painter = painterResource(id = R.drawable.ic_close),
                shape = CircleShape,
                onClick = onCloseClick,
                size = 20.dp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn {
            items(
                items = patientList.distinct()
            ) { patient ->
                val selected = selectedPatient == patient
                PatientCard(
                    patient = patient,
                    selected = selected,
                    onSelect = {
                        selectedPatient = it
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Добавить пациента",
            onClick = {
                val intent = Intent(mContext, CreateCardActivity::class.java)
                intent.putExtra("isNew", true)

                mContext.startActivity(intent)
            },
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            ),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Подтвердить",
            enabled = selectedPatient != null,
            onClick = {
                onSelectClick(selectedPatient!!)
            }
        )
    }
}
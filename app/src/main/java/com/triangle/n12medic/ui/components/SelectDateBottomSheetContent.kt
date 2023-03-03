package com.triangle.n12medic.ui.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import java.text.SimpleDateFormat
import java.util.Calendar

// Экран выбора даты
@SuppressLint("SimpleDateFormat")
@Composable
fun SelectDateBottomSheetContent(
    onCloseClick: () -> Unit,
    onTimePick: (String) -> Unit
) {
    val mContext = LocalContext.current
    var date by rememberSaveable { mutableStateOf("") }
    var displayDate by rememberSaveable { mutableStateOf("") }

    var selectedTime by rememberSaveable { mutableStateOf("") }
    val times = listOf(
        "10:00", "13:00", "14:00", "15:00", "16:00", "18:00", "19:00"
    )

    val displayDateFormat = SimpleDateFormat("dd MMMM")
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    val calendar = Calendar.getInstance()

    val mDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
    val mMonth: Int = calendar.get(Calendar.MONTH)
    val mYear: Int = calendar.get(Calendar.YEAR)

    val datePicker = DatePickerDialog(
        mContext,
        { _, year: Int, month: Int, day: Int ->
            val d = simpleDateFormat.parse("$day.$month.$year")

            displayDate = displayDateFormat.format(d)
        }, mYear, mMonth, mDay
    )

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
                "Дата и время",
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
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = displayDate,
            onValueChange = { displayDate = it },
            readOnly = true,
            label = {
                Text(
                    text = "Выберите дату",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF7E7E9A)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { datePicker.show() }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_down), contentDescription = "")
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Выберите время",
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            color = Color(0xFF7E7E9A)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(78.dp),
            content = {
                items(
                    items = times
                ) { t ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .width(70.dp)
                                .height(40.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable(
                                    onClick = {
                                        selectedTime = t
                                    }
                                ),
                            elevation = 0.dp,
                            backgroundColor = if (selectedTime == t) MaterialTheme.colors.primary else Color(0xFFF5F5F9)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    text = t,
                                    fontSize = 16.sp,
                                    color = if (selectedTime == t) Color.White else Color(0xFF7E7E9A)
                                )
                            }
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(56.dp))
        AppButton(
            label = "Подтвердить",
            onClick = { onTimePick("$displayDate $selectedTime") }
        )
    }
}
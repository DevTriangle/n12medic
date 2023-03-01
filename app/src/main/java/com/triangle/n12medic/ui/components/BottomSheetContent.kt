package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.model.Analysis
import com.triangle.n12medic.R

@Composable
fun AnalysisBottomSheetContent(
    analysis: Analysis?,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
    Box() {
        Text(text = "")
        if (analysis != null) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    Text(
                        modifier = Modifier
                            .weight(9f),
                        text = analysis.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    AppIconButton(
                        painter = painterResource(id = R.drawable.ic_close),
                        onClick = onDismiss,
                        shape = CircleShape,
                        size = 28.dp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Описание",
                    color = Color(0xFF939396),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = analysis.description,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Подготовка",
                    color = Color(0xFF939396),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = analysis.preparation,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(55.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = "Результаты через:",
                            color = Color(0xFF939396),
                            fontSize = 14.sp,
                        )
                        Text(
                            text = analysis.timeResult,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column() {
                        Text(
                            text = "Биоматериал:",
                            color = Color(0xFF939396),
                            fontSize = 14.sp,
                        )
                        Text(
                            text = analysis.bio,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                AppButton(
                    label = "Добавить за ${analysis.price} ₽",
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

}
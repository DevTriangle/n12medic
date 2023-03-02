package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultStrokeLineMiter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnalysisSearchCard(
    name: String,
    searchValue: String,
    price: String,
    timeResult: String
) {
    val text = name.split(searchValue)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color(0xFFF4F4F4),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = DefaultStrokeLineMiter
                )
            }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .weight(7f),
            text = buildAnnotatedString {
                for ((index, t) in text.withIndex()) {
                    append(t)
                    if (index != text.size - 1) {
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                            append(searchValue)
                        }
                    }
                }
            },
            fontSize = 15.sp
        )
        Column(
            modifier = Modifier
                .weight(3f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${price} ₽",
                fontSize = 17.sp,
                textAlign = TextAlign.End
            )
            Text(
                text = timeResult,
                fontSize = 14.sp,
                color = Color(0xFF939396),
                textAlign = TextAlign.End
            )
        }
    }
}
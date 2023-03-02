package com.triangle.n12medic.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.model.Analysis

@Composable
fun CatalogCard(
    modifier: Modifier = Modifier,
    name: String,
    timeResult: String,
    price: String,
    onClick: () -> Unit,
    isInCart: Boolean = false
) {
    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .border(1.dp, Color(0xFFF4F4F4), MaterialTheme.shapes.medium)
            .shadow(
                elevation = 10.dp,
                shape = MaterialTheme.shapes.medium,
                spotColor = Color.Black.copy(0.2f)
            )
            .background(Color.White, shape = MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = timeResult,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF939396)
                    )
                    Text(
                        text = "${price} ₽",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                AppButton(
                    label = if (isInCart) "Убрать" else "Добавить",
                    onClick = onClick,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isInCart) Color.White else MaterialTheme.colors.primary,
                        contentColor = if (isInCart) MaterialTheme.colors.primary else Color.White
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                )
            }
        }
    }
}
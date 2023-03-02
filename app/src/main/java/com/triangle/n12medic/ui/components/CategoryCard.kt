package com.triangle.n12medic.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryChip(
    selected: Boolean,
    label: String,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = { onClick(label) }
            ),
        backgroundColor = if (selected) MaterialTheme.colors.primary else Color(0xFFF5F5F9),
        elevation = 0.dp
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 14.dp),
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) Color.White else Color(0xFF7E7E9A),
        )
    }
}
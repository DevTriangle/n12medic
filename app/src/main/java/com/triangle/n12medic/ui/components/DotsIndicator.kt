package com.triangle.n12medic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.triangle.n12medic.ui.theme.primaryColor

@Composable
fun DotsIndicator(
    count: Int,
    currentIndex: Int
) {
    Row() {
        for (i in 0 until count) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .size(12.5.dp)
                    .clip(CircleShape)
                    .background(
                        if (currentIndex == i) primaryColor else Color.White
                    )
                    .border(1.dp, primaryColor, CircleShape)
            )
        }
    }
}
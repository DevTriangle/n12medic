package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    content: @Composable() (RowScope.() -> Unit)
) {
    BottomNavigation(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    Color.Black.copy(0.1f),
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    Stroke.DefaultMiter
                )
            },
        backgroundColor = Color.White,
        content = content,
        elevation = 2.dp
    )
}
package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    content: @Composable() (RowScope.() -> Unit)
) {
    BottomNavigation(
        modifier = Modifier,
        backgroundColor = Color.White,
        content = content,
        elevation = 1.dp
    )
}
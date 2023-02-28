package com.triangle.n12medic.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R

// Set of Material typography styles to start with

val SFProFamily = FontFamily(
    Font(R.font.sf_light, FontWeight.Light),
    Font(R.font.sf_regular, FontWeight.Normal),
    Font(R.font.sf_medium, FontWeight.Medium),
    Font(R.font.sf_semibold, FontWeight.SemiBold),
    Font(R.font.sf_bold, FontWeight.Bold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = SFProFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)
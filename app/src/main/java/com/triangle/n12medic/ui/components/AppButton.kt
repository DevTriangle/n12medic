package com.triangle.n12medic.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.theme.primaryColor
import com.triangle.n12medic.ui.theme.primaryVariantColor

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    ),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = primaryColor,
        disabledBackgroundColor = Color(0xFFC9D4FB),
        disabledContentColor = Color.White
    ),
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 16.dp),
    onClick: () -> Unit,
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    icon: Painter? = null
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        contentPadding = contentPadding,
        elevation = elevation,
        border = border
    ) {
        Row() {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = "",
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                )
            }
            Text(
                text = label,
                style = textStyle
            )
        }
    }
}

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    ),
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 16.dp),
    onClick: () -> Unit
) {
    AppButton(
        modifier = modifier
            .border(1.dp, Color(0xFFEBEBEB), MaterialTheme.shapes.medium),
        label = label,
        enabled = enabled,
        contentPadding = contentPadding,
        onClick = onClick,
        textStyle = textStyle,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
    )
}

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = primaryVariantColor
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    textStyle: TextStyle = LocalTextStyle.current
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
    ) {
        Text(
            text = label,
            style = textStyle.copy(colors.contentColor(true).value)
        )
    }
}

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    size: Dp = 32.dp,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color(0xFFF5F5F9),
        contentColor = Color(0xFF7E7E9A),
        disabledContentColor = Color.White
    ),
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
    onClick: () -> Unit,
    elevation: ButtonElevation = ButtonDefaults.elevation(
        0.dp, 0.dp, 0.dp
    ),
    shape: Shape = MaterialTheme.shapes.medium
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(colors.backgroundColor(enabled = true).value, shape)
            .clickable(
                onClick = onClick
            )
    ) {
        Icon(
            modifier = Modifier
                .padding(contentPadding)
                .size(size - 7.dp),
            painter = painter,
            contentDescription = "",
            tint = Color(0xFF7E7E9A)
        )
    }
}

@Composable
fun PasswordKeyButton(
    number: Int,
    onClick: (Int) -> Unit,
    size: Dp = 80.dp,
) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .size(size),
        onClick = { onClick(number) },
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFF5F5F9),
        ),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape
    ) {
        Text(
            text = number.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CartButton(
    price: Int,
    onClick: () -> Unit
) {
//        var cartPrice = 0.0
//        for (item in inCart) {
//            for (i in viewModel.analyzes) {
//                if (item == i.id.toDouble()) {
//                    cartPrice += i.price.toDouble()
//                    break
//                }
//            }
//        }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.primary)
            .clickable(
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "В корзину",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                "${price} ₽",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CartCountButtons(
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    count: Int
) {
    Card(
        modifier = Modifier
            .height(32.dp),
        elevation = 0.dp,
        backgroundColor = Color(0xFFF5F5F9)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onMinusClick,
                enabled = count > 1
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "",
                    tint = if (count < 2) Color(0xFFB8C1CC) else Color(0xFF939396)
                )
            }
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(0.75f)
                    .background(Color(0xFFEBEBEB))
            )
            IconButton(
                onClick = onPlusClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "",
                    tint = Color(0xFF939396)
                )
            }
        }
    }
}
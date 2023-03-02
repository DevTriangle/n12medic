package com.triangle.n12medic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.model.CartItem

@Composable
fun CartComponent(
    modifier: Modifier = Modifier,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onRemoveClick: () -> Unit,
    cartItem: CartItem
) {
    Column(
        modifier = modifier
            .padding(bottom = 16.dp)
            .border(1.dp, Color(0xFFF4F4F4), MaterialTheme.shapes.medium)
            .shadow(
                elevation = 10.dp,
                shape = MaterialTheme.shapes.medium,
                spotColor = Color.Black.copy(0.2f)
            )
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier
                        .weight(9f),
                    text = cartItem.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = onRemoveClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val price = cartItem.price.toDouble() * cartItem.count
                Text(
                    text = "${price} ₽",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    Text(text = "${cartItem.count} пациент")
                    Spacer(modifier = Modifier.width(16.dp))
                    CartCountButtons(
                        onPlusClick = onPlusClick,
                        onMinusClick = onMinusClick,
                        count = cartItem.count
                    )
                }
            }
    }
}
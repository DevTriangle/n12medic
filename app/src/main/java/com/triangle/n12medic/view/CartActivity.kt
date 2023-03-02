package com.triangle.n12medic.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.common.CartService
import com.triangle.n12medic.model.CartItem
import com.triangle.n12medic.ui.components.AppIconButton
import com.triangle.n12medic.ui.components.CartComponent
import com.triangle.n12medic.ui.theme.N12MedicTheme

class CartActivity : ComponentActivity() {
    // Корзина клиента
    // Дата создания: 01.03.2023 16:08
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CartContent()
                }
            }
        }
    }

    @Composable
    fun CartContent() {
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)

        val cart: MutableList<CartItem> = remember { mutableStateListOf() }
        cart.addAll(CartService().loadCart(sharedPreferences))

        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    AppIconButton(
                        painter = painterResource(id = R.drawable.ic_back),
                        onClick = { onBackPressed() }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Корзина",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        IconButton(
                            onClick = {
                                cart.clear()
                                CartService().saveCart(sharedPreferences, cart)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_remove),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(
                            items = cart
                        ) { item ->
                            CartComponent(
                                onPlusClick = {
                                    val index = cart.indexOf(item)
                                    cart.removeAt(index)
                                    cart.add(index, CartItem(
                                        item.id, item.name, item.price, item.count + 1
                                    ))

                                    CartService().saveCart(sharedPreferences, cart)
                                },
                                onMinusClick = {
                                    val index = cart.indexOf(item)
                                    cart.removeAt(index)
                                    cart.add(index, CartItem(
                                        item.id, item.name, item.price, item.count - 1
                                    ))

                                    CartService().saveCart(sharedPreferences, cart)
                                },
                                cartItem = item,
                                onRemoveClick = {
                                    cart.remove(item)

                                    CartService().saveCart(sharedPreferences, cart)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
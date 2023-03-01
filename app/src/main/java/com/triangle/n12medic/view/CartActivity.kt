package com.triangle.n12medic.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.components.AppIconButton
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
                            onClick = { }
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
                
            }
        }
    }
}
package com.triangle.n12medic.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.ui.components.AppTextField
import com.triangle.n12medic.R
import com.triangle.n12medic.model.News
import com.triangle.n12medic.ui.components.CategoryChip
import com.triangle.n12medic.ui.components.NewsComponent
import com.triangle.n12medic.viewmodel.HomeViewModel

@Composable
fun AnalyzesScreen(
    viewModel: HomeViewModel
) {
    var searchValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchValue,
            onValueChange = {
                searchValue = it
            },
            label = "Искать анализы",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        NewsContainer(viewModel)
        Spacer(modifier = Modifier.height(32.dp))
        AnalyzesContainer(viewModel)
    }
}

@Composable
private fun NewsContainer(
    viewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.loadNews()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Акции и новости",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF939396)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow() {
            items(
                items = viewModel.news
            ) { n ->
                NewsComponent(
                    news = n
                )
            }
        }
    }
}

@Composable
private fun AnalyzesContainer(
    viewModel: HomeViewModel
) {
    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Каталог анализов",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF939396)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow() {
            items(
                items = viewModel.analyzesCategories
            ) { category ->
                CategoryChip(
                    selected = category == selectedCategory,
                    label = category,
                    onClick = { selectedCategory = it }
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn() {

        }
    }
}
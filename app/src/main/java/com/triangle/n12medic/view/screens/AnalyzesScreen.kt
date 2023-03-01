package com.triangle.n12medic.view.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.triangle.n12medic.R
import com.triangle.n12medic.model.News
import com.triangle.n12medic.ui.components.*
import com.triangle.n12medic.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun AnalyzesScreen(
    viewModel: HomeViewModel
) { // Главный экран
    var searchValue by rememberSaveable { mutableStateOf("") }
    var isVisible by rememberSaveable { mutableStateOf(true) }

    var isErrorVisible by rememberSaveable { mutableStateOf(false) }

    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

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
        Spacer(modifier = Modifier.height(16.dp))
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.loadCatalog()
                viewModel.loadNews()
            }
        ) {
            Column {
                AnimatedVisibility(visible = isVisible) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        NewsContainer(viewModel)
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Каталог анализов",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF939396)
                        )
                    }
                }
                AnalyzesContainer(
                    viewModel
                ) {
                    isVisible = it
                }
            }
        }
    }
}

@Composable
private fun NewsContainer(
    viewModel: HomeViewModel
) {
    var isErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val errorMessage by viewModel.newsErrorMessage.observeAsState()
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            isErrorVisible = true
        }
    }

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

    if (isErrorVisible) {
        AlertDialog(
            onDismissRequest = { isErrorVisible = false },
            title = {
                Text(
                    text = "Ошибка",
                    fontSize = 18.sp
                )
            },
            text = {
                errorMessage?.let { Text(text = it) }
            },
            buttons = {
                AppTextButton(
                    label = "Ок",
                    onClick = {
                        isErrorVisible = false
                    }
                )
            }
        )
    }
}

@Composable
private fun AnalyzesContainer(
    viewModel: HomeViewModel,
    onScrollChange: (Boolean) -> Unit
) {
    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }
    val scrollState = rememberLazyListState()

    var isErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val errorMessage by viewModel.analyzesErrorMessage.observeAsState()
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            isErrorVisible = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCatalog()
    }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemScrollOffset }.collect() {
            Log.d(TAG, "AnalyzesContainer: $it")
            if (it < 100) {
                onScrollChange(true)
            } else {
                onScrollChange(false)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
        LazyColumn(
            state = scrollState
        ) {
            items(
                items = viewModel.analyzes
            ) { analysis ->
                if (analysis.category.lowercase() == selectedCategory.lowercase()) {
                    CatalogCard(
                        analysis = analysis,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    if (isErrorVisible) {
        AlertDialog(
            onDismissRequest = { isErrorVisible = false },
            title = {
                Text(
                    text = "Ошибка",
                    fontSize = 18.sp
                )
            },
            text = {
                errorMessage?.let { Text(text = it) }
            },
            buttons = {
                AppTextButton(
                    label = "Ок",
                    onClick = {
                        isErrorVisible = false
                    }
                )
            }
        )
    }
}
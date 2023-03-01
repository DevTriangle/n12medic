package com.triangle.n12medic.view.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import com.triangle.n12medic.R
import com.triangle.n12medic.model.Analysis
import com.triangle.n12medic.model.News
import com.triangle.n12medic.ui.components.*
import com.triangle.n12medic.view.CartActivity
import com.triangle.n12medic.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnalyzesScreen(
    viewModel: HomeViewModel
) { // Главный экран
    val mContext = LocalContext.current
    var searchValue by rememberSaveable { mutableStateOf("") }
    var isVisible by rememberSaveable { mutableStateOf(true) }

    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    val inCartJson = sharedPreferences.getString("cart", "[]")
    val inCart: ArrayList<Double> = Gson().fromJson(inCartJson, ArrayList<Double>().javaClass)

    Log.d(TAG, "AnalyzesScreen: $inCart")

    var selectedAnalysis: Analysis? by rememberSaveable { mutableStateOf(null) }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {
            if (it.ordinal == 0) {
                selectedAnalysis = null
            }
            true
        }
    )

    val scope = rememberCoroutineScope()

    var isErrorVisible by rememberSaveable { mutableStateOf(false) }

    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }
    val scrollState = rememberLazyListState()

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
            isVisible = it == 0
        }
    }

    ModalBottomSheetLayout(
        sheetShape = MaterialTheme.shapes.large.copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)),
        sheetContent = {
                AnalysisBottomSheetContent(
                    analysis = selectedAnalysis,
                    onDismiss = {
                        scope.launch {
                            selectedAnalysis = null
                            bottomSheetState.hide()
                        }
                    },
                    onClick = {
                        if (selectedAnalysis != null) {
                            inCart.add(selectedAnalysis!!.id.toDouble())

                            with(sharedPreferences.edit()) {
                                val jsonCart = Gson().toJson(inCart)
                                putString("cart", jsonCart)
                                apply()
                            }
                        }
                        scope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )
        },
        sheetState = bottomSheetState
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
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
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
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
                                        var itemInCart = false
                                        for (i in inCart) {
                                            if (i == analysis.id.toDouble()) {
                                                itemInCart = true
                                            }
                                        }

                                        CatalogCard(
                                            analysis = analysis,
                                            onClick = {
                                                if (!itemInCart) {
                                                    selectedAnalysis = analysis
                                                    scope.launch {
                                                        bottomSheetState.show()
                                                    }
                                                } else {
                                                    inCart.remove(analysis.id.toDouble())
                                                    selectedAnalysis = null
                                                }
                                            },
                                            isInCart = itemInCart
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
                }
            }
            AnimatedVisibility(
                modifier = Modifier
                    .background(Color.White)
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                visible = inCart.size > 0
            ) {
                var cartPrice = 0.0
                for (item in inCart) {
                    for (i in viewModel.analyzes) {
                        if (item == i.id.toDouble()) {
                            cartPrice += i.price.toDouble()
                            break
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                        .clickable(
                            onClick = {
                                val intent = Intent(mContext, CartActivity::class.java)
                                mContext.startActivity(intent)
                            }
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
                            "${cartPrice} ₽",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
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
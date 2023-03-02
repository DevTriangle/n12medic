package com.triangle.n12medic.view.screens

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.triangle.n12medic.R
import com.triangle.n12medic.common.CartService
import com.triangle.n12medic.model.CartItem
import com.triangle.n12medic.ui.components.*
import com.triangle.n12medic.view.CartActivity
import com.triangle.n12medic.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnalyzesScreen(
    viewModel: HomeViewModel
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    var searchValue by rememberSaveable { mutableStateOf("") }

    val lazyListState = rememberLazyListState()

    val analyzesCategories = arrayListOf(
        "Популярные",
        "Covid",
        "Комплексные",
        "Чекапы",
        "Биохимия",
        "Гормоны",
        "Иммунитет",
        "Витамины",
        "Аллергены",
        "Анализ крови",
        "Анализ мочи",
        "Анализ кала",
        "Только в клинике"
    )

    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }

    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    LaunchedEffect(Unit) {
        viewModel.loadNews()
        viewModel.loadCatalog()
    }

    var isNewsVisible by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset }.collect {
            isNewsVisible = it == 0
        }
    }

    var isAlertDialogVisible by rememberSaveable { mutableStateOf(false) }

    val message by viewModel.message.observeAsState()
    LaunchedEffect(message) {
        if (message != null) {
            isAlertDialogVisible = true
        }
    }

    var id by rememberSaveable { mutableStateOf(0) }
    var title by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }
    var preparation by rememberSaveable { mutableStateOf("") }
    var timeResult by rememberSaveable { mutableStateOf("") }
    var bio by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }

    val cart: MutableList<CartItem> = remember { mutableStateListOf() }
    cart.addAll(CartService().loadCart(sharedPreferences))

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Text(
                        title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                    )
                    AppIconButton(
                        painter = painterResource(id = com.triangle.n12medic.R.drawable.ic_close),
                        shape = CircleShape,
                        onClick = {
                            scope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                Text(
                    "Описание",
                    color = Color(0xFF939396),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    descriptionText,
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Подготовка",
                    color = Color(0xFF939396),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    preparation,
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Результаты через:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF939396)
                        )
                        Text(
                            timeResult,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Биоматериал:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF939396)
                        )
                        Text(
                            bio,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                    }
                }
                Spacer(modifier = Modifier.height(19.dp))
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    label = "Добавить за $price ₽",
                    onClick = {
                        val index = cart.indexOfFirst { it.id == id}
                        if (index != -1) {
                            val lastCount = cart[index].count
                            cart.removeAt(index)
                            cart.add(
                                CartItem(
                                    id = id,
                                    name = title,
                                    price = price,
                                    count = lastCount + 1
                                )
                            )
                        } else {
                            cart.add(
                                CartItem(
                                    id = id,
                                    name = title,
                                    price = price,
                                    count = 1
                                )
                            )
                        }

                        CartService().saveCart(sharedPreferences, cart)

                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetState = modalBottomSheetState
    ) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                isRefreshing = true
                viewModel.loadNews()
                viewModel.loadCatalog()
                isRefreshing = false
            }
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                AppTextField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    contentPadding = PaddingValues(14.dp),
                    placeholder = {
                        Text(
                            "Искать анализы",
                            fontSize = 16.sp,
                            color = Color(0xFF939396)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "",
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(30.dp))
                AnimatedVisibility(visible = isNewsVisible) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            "Акции и новости",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF939396)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyRow {
                            items(viewModel.news) { item ->
                                NewsComponent(news = item)
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            "Каталог анализов",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF939396)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    for (category in analyzesCategories) {
                        CategoryChip(
                            selected = selectedCategory == category,
                            label = category,
                            onClick = { selectedCategory = category }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
                LazyColumn(state = lazyListState) {
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    items(viewModel.analyzes.filter { it.category.lowercase() == selectedCategory.lowercase() }.distinct()) { item ->
                        var isElementInCart = false

                        for (i in cart) {
                            if (i.id == item.id) {
                                isElementInCart = true
                                break
                            }
                        }
                        CatalogCard(
                            name = item.name,
                            price = item.price,
                            timeResult = item.timeResult,
                            isInCart = isElementInCart,
                            onClick = {
                                id = item.id
                                title = item.name
                                descriptionText = item.description
                                preparation = item.preparation
                                timeResult = item.timeResult
                                bio = item.bio
                                price = item.price

                                scope.launch {
                                    modalBottomSheetState.show()
                                }
                            }
                        )
                    }
                }
            }

            if (isAlertDialogVisible) {
                AlertDialog(
                    onDismissRequest = {
                        isAlertDialogVisible = false
                    },
                    title = {
                        Text(
                            "Ошибка",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W700
                        )
                    },
                    text = {
                        Text(
                            viewModel.message.value.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W700
                        )
                    },
                    buttons = {
                        AppTextButton(
                            label = "OK",
                            onClick = { isAlertDialogVisible = false }
                        )
                    }
                )
            }
        }

        AnimatedVisibility(visible = cart.size > 0) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(20.dp)
                        .align(Alignment.BottomCenter)
                        .zIndex(
                            if (modalBottomSheetState.isVisible) {
                                -1f
                            } else {
                                10f
                            }
                        )
                ) {
                    var sum = 0.0

                    for (item in cart) {
                        sum += item.price.toInt() * item.count
                    }

                    CartButton(
                        price = sum.toInt(),
                    ) {
                        val intent = Intent(mContext, CartActivity::class.java)
                        mContext.startActivity(intent)
                    }
                }
            }
        }
    }
}
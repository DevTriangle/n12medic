package com.triangle.n12medic.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.triangle.n12medic.ui.components.BottomNavigationBar
import com.triangle.n12medic.ui.theme.N12MedicTheme
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.theme.iconColor
import com.triangle.n12medic.view.screens.AnalyzesScreen
import com.triangle.n12medic.viewmodel.HomeViewModel

class HomeActivity : ComponentActivity() {
    // Класс экрана с отображением главной страницы
    // Дата создания: 01.03.2023 8:47
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreenContent()
                }
            }
        }
    }

    @Composable
    fun MainScreenContent() {
        val navHostController = rememberNavController()
        val navBackStackEntry = navHostController.currentBackStackEntryAsState()

        Scaffold(
            content = {
                Box(modifier = Modifier.padding(it)) {
                    Navigation(navHostController = navHostController)
                }
            },
            bottomBar = {
                BottomNavigationBar {
                    BottomNavigationItem(
                        selected = navBackStackEntry.value?.destination?.route == "analyzes",
                        onClick = {
                            if (navBackStackEntry.value?.destination?.route != "analyzes") {
                                navHostController.navigate("analyzes")
                            }
                        },
                        label = { Text(
                            text = "Анализы",
                            fontSize = 12.sp
                        ) },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_analyzes),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = iconColor
                    )
                    BottomNavigationItem(
                        selected = navBackStackEntry.value?.destination?.route == "results",
                        onClick = {
                            if (navBackStackEntry.value?.destination?.route != "results") {
                                navHostController.navigate("results")
                            }
                        },
                        label = { Text(
                            text = "Результаты",
                            fontSize = 12.sp
                        ) },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_result),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = iconColor
                    )
                    BottomNavigationItem(
                        selected = navBackStackEntry.value?.destination?.route == "support",
                        onClick = {
                            if (navBackStackEntry.value?.destination?.route != "support") {
                                navHostController.navigate("support")
                            }
                        },
                        label = { Text(
                            text = "Поддержка",
                            fontSize = 12.sp
                        ) },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_support),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = iconColor
                    )
                    BottomNavigationItem(
                        selected = navBackStackEntry.value?.destination?.route == "profile",
                        onClick = {
                            if (navBackStackEntry.value?.destination?.route != "profile") {
                                navHostController.navigate("profile")
                            }
                        },
                        label = { Text(
                            text = "Профиль",
                            fontSize = 12.sp
                        ) },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = iconColor
                    )
                }
            },
        )
    }

    @Composable
    private fun Navigation(navHostController: NavHostController) {
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        NavHost(
            navController = navHostController,
            startDestination = "analyzes"
        ) {
            composable("analyzes") {
                AnalyzesScreen(viewModel)
            }
            composable("results") {
            }
            composable("support") {
            }
            composable("profile") {
            }
        }
    }
}
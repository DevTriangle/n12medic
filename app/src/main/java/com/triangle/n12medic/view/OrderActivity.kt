package com.triangle.n12medic.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.ui.components.AddressBottomSheetContent
import com.triangle.n12medic.ui.components.AppIconButton
import com.triangle.n12medic.ui.components.AppTextField
import com.triangle.n12medic.ui.theme.N12MedicTheme
import kotlinx.coroutines.launch

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12MedicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OrderScreen()
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun OrderScreen() {
        val mContext = LocalContext.current
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        var address by rememberSaveable { mutableStateOf("") }

        var currentExpanded by rememberSaveable { mutableStateOf("address") }

        val addressInteractionSource = remember { MutableInteractionSource() }
        if (addressInteractionSource.collectIsPressedAsState().value) {
            currentExpanded = "address"
            scope.launch { bottomSheetState.show() }
        }

        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                when(currentExpanded) {
                    "address" -> {
                        AddressBottomSheetContent(
                            onMapIconClick = {

                            }
                        )
                    }
                    "date" -> {

                    }
                    "patient" -> {

                    }
                }
            },
            content = {
                Scaffold(
                    topBar = {
                        AppIconButton(
                            painter = painterResource(id = R.drawable.ic_back),
                            onClick = { onBackPressed() }
                        )
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        Column( modifier = Modifier
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())) {
                            Text(
                                text = "Оформление заказа",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            AppTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = address,
                                onValueChange = { address = it },
                                readOnly = true,
                                interactionSource = addressInteractionSource,
                                label = {
                                    Text(
                                        text = "Адрес *",
                                        fontSize = 14.sp,
                                        color = Color(0xFF7E7E9A)
                                    )
                                },
                                placeholder = {
                                    Text(text = "Введите ваш адрес")
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}
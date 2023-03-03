package com.triangle.n12medic.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic.R
import com.triangle.n12medic.common.CartService
import com.triangle.n12medic.common.PatientService
import com.triangle.n12medic.model.CartItem
import com.triangle.n12medic.model.Patient
import com.triangle.n12medic.ui.components.*
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
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)

        val cart: MutableList<CartItem> = remember { mutableStateListOf() }
        val patientList: MutableList<Patient> = remember { mutableStateListOf() }
        val selectedPatientList: MutableList<Patient> = remember { mutableStateListOf() }
        LaunchedEffect(Unit) {
            cart.addAll(CartService().loadCart(sharedPreferences))
            val tPatientList = PatientService().loadPatientList(sharedPreferences)
            for (p in tPatientList) {
                patientList.add(Patient(
                    p.firstName,
                    p.lastName,
                    p.middlename,
                    p.bith,
                    p.pol,
                    p.image,
                    cart
                ))

                Log.d(TAG, "patientList.add: ${cart.size}")
                Log.d(TAG, "patientList.size: ${patientList.size}")
            }

            if (patientList.isNotEmpty()) {
                selectedPatientList.add(patientList[0])
            }
        }

        val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        var address by rememberSaveable { mutableStateOf("") }
        var date by rememberSaveable { mutableStateOf("") }
        var phone by rememberSaveable { mutableStateOf("") }
        var comment by rememberSaveable { mutableStateOf("") }

        var currentExpanded by rememberSaveable { mutableStateOf("address") }

        var editPatient: Patient? by remember { mutableStateOf(null) }

        val dateInteractionSource = remember { MutableInteractionSource() }
        if (dateInteractionSource.collectIsPressedAsState().value) {
            currentExpanded = "date"
            scope.launch { bottomSheetState.show() }
        }

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

                            },
                            onAddressSelect = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                                address = it
                            }
                        )
                    }
                    "date" -> {
                        SelectDateBottomSheetContent(
                            onCloseClick = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            },
                            onTimePick = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                                date = it
                            }
                        )
                    }
                    "patient" -> {
                        SelectPatientBottomSheetContent(
                            patientList = patientList,
                            onCloseClick = {
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            },
                            onSelectClick = { p ->
                                scope.launch { bottomSheetState.hide() }
                                if (editPatient != null) {
                                    val index = selectedPatientList.indexOf(editPatient)
                                    selectedPatientList[index] = p

                                    //selectedPatientList.add(p)
                                } else {
                                    if (!selectedPatientList.contains(p)) selectedPatientList.add(p)
                                }
                                Log.d(TAG, "OrderScreen: ${selectedPatientList.size}")
                            }
                        )
                    }
                }
            },
            content = {
                Scaffold(
                    topBar = {
                        AppIconButton(
                            modifier = Modifier
                                .padding(20.dp),
                            painter = painterResource(id = R.drawable.ic_back),
                            onClick = { onBackPressed() }
                        )
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                            ) {
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
                                        Text(
                                            text = "Введите ваш адрес",
                                            color = Color.Black
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                AppTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = date,
                                    onValueChange = { date = it },
                                    readOnly = true,
                                    interactionSource = dateInteractionSource,
                                    label = {
                                        Text(
                                            text = "Дата и время*",
                                            fontSize = 14.sp,
                                            color = Color(0xFF7E7E9A)
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Выберите дату и время",
                                            color = Color.Black
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        append("Кто будет сдавать анализы?")
                                        withStyle(style = SpanStyle(color = MaterialTheme.colors.error)) {
                                            append("*")
                                        }
                                    },
                                    fontSize = 14.sp,
                                    color = Color(0xFF7E7E9A)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Column() {
                                    if (selectedPatientList.size < 2) {
                                        for (patient in selectedPatientList) {
                                            val patientInteractionSource = remember { MutableInteractionSource() }
                                            if (patientInteractionSource.collectIsPressedAsState().value) {
                                                currentExpanded = "patient"
                                                editPatient = patient
                                                scope.launch { bottomSheetState.show() }
                                            }

                                            AppTextField(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 16.dp),
                                                value = "${patient.lastName} ${patient.firstName}",
                                                onValueChange = {},
                                                readOnly = true,
                                                interactionSource = patientInteractionSource,
                                                trailingIcon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_down),
                                                        contentDescription = "",
                                                        tint = Color(0xFF7E7E9A)
                                                    )
                                                },
                                                leadingIcon = {
                                                    Image(
                                                        painter = if (patient.pol == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                                                        contentDescription = ""
                                                    )
                                                }
                                            )
                                        }
                                    } else {
                                        for (patient in selectedPatientList) {
                                            val patientInteractionSource = remember { MutableInteractionSource() }
                                            if (patientInteractionSource.collectIsPressedAsState().value) {
                                                currentExpanded = "patient"
                                                editPatient = patient
                                                scope.launch { bottomSheetState.show() }
                                            }

                                            OrderPatientCard(
                                                patient = patient,
                                                cart = cart,
                                                interactionSource = patientInteractionSource,
                                                onRemoveClick = {
                                                    selectedPatientList.remove(patient)
                                                },
                                                onPatientCartChange = { cart ->
                                                    val index = selectedPatientList.indexOf(patient)
                                                    selectedPatientList.remove(patient)

                                                    selectedPatientList.add(index, Patient(
                                                        firstName = patient.firstName,
                                                        lastName = patient.lastName,
                                                        middlename = patient.middlename,
                                                        bith = patient.bith,
                                                        pol = patient.pol,
                                                        image = patient.image,
                                                        cart = cart
                                                    ))
                                                }
                                            )
                                            Spacer(modifier = Modifier.height(16.dp))

                                            Log.d(TAG, "OrderScreen CART: ${cart.size}")
                                        }
                                    }
                                }
                                AppButton(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    label = "Добавить еще пациента",
                                    onClick = {
                                        currentExpanded = "patient"
                                        editPatient = null
                                        scope.launch { bottomSheetState.show() }
                                    },
                                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White,
                                        contentColor = MaterialTheme.colors.primary
                                    ),
                                    textStyle = LocalTextStyle.current.copy(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal
                                    ),
                                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp)
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                AppTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = phone,
                                    onValueChange = { phone = it },
                                    label = {
                                        Text(
                                            text = "Телефон *",
                                            fontSize = 14.sp,
                                            color = Color(0xFF7E7E9A)
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "+7 (967) 078-58-37",
                                            color = Color.Black
                                        )
                                    }
                                )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Комментарий",
                                            fontSize = 14.sp,
                                            color = Color(0xFF7E7E9A)
                                        )
                                        IconButton(
                                            onClick = {
                                                // todo micro
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_micro),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(20.dp)
                                            )
                                        }
                                    }
                                    AppTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(128.dp),
                                        value = comment,
                                        onValueChange = { comment = it },
                                        placeholder = {
                                            Text(
                                                text = "Можете оставить свои пожелания",
                                                color = Color(0xFF939396)
                                            )
                                        }
                                    )
                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            Column(
                                modifier = Modifier
                                    .background(Color(0xFFF5F5F9))
                                    .padding(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Оплата Apple Pay",
                                        color = Color.Black
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right),
                                        contentDescription = "",
                                        tint = Color(0xFFB8C1CC)
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Промокод",
                                        color = Color(0xFFB8C1CC)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right),
                                        contentDescription = "",
                                        tint = Color(0xFFB8C1CC)
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    var count = 0
                                    for (patient in selectedPatientList.distinct()) {
                                        count += patient.cart?.size ?: 0
                                    }
                                    Text(
                                        text = "$count анализ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    var sum = 0
                                    for (patient in selectedPatientList.distinct()) {
                                        for (item in patient.cart!!) {
                                            sum += item.price.toInt()
                                        }
                                    }
                                    Text(
                                        text = "${sum} ₽",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                AppButton(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    label = "Заказать",
                                    enabled = address != "" && date != "",
                                    onClick = {

                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
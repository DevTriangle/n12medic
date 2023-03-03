package com.triangle.n12medic.view.screens

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.triangle.n12medic.R
import com.triangle.n12medic.common.PatientService
import com.triangle.n12medic.model.CartItem
import com.triangle.n12medic.model.Patient
import com.triangle.n12medic.ui.components.AppButton
import com.triangle.n12medic.ui.components.AppTextField
import com.triangle.n12medic.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    resultLauncher: ActivityResultLauncher<Intent>,
    navController: NavHostController
) { // Экран профиля пользователя
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", ComponentActivity.MODE_PRIVATE)

    val patientList: MutableList<Patient> = remember { mutableStateListOf() }
    patientList.addAll(PatientService().loadPatientList(sharedPreferences))

    var firstName by rememberSaveable { mutableStateOf(patientList[0].firstName) }
    var patronymic by rememberSaveable { mutableStateOf(patientList[0].middlename) }
    var lastName by rememberSaveable { mutableStateOf(patientList[0].lastName) }
    var birthday by rememberSaveable { mutableStateOf(patientList[0].bith) }
    var gender by rememberSaveable { mutableStateOf(patientList[0].pol) }

    if (patientList.isNotEmpty()) {
        firstName = patientList[0].firstName
        patronymic = patientList[0].middlename
        lastName = patientList[0].lastName
        birthday = patientList[0].bith
        gender = patientList[0].pol
        viewModel.setImage(patientList[0].image)
    }

    var genderExpanded by rememberSaveable { mutableStateOf(false) }
    val token = sharedPreferences.getString("token", "")

    var imageUpdated by remember { mutableStateOf(false) }
    val image by viewModel.selectedImage.observeAsState()
    LaunchedEffect(image) {
        if (image != null) {
            imageUpdated = true
        }
    }

    val isSuccess by viewModel.isSuccess.observeAsState()

    val isSuccessUploadMessage by viewModel.isSuccessUploadImage.observeAsState()
    LaunchedEffect(isSuccessUploadMessage) {
        if (isSuccessUploadMessage == true && isSuccess == true) {
            patientList.add(0, Patient(firstName, lastName, patronymic, birthday, gender, image, ArrayList<CartItem>().toMutableList()))
            PatientService().savePatientList(sharedPreferences, patientList)

            navController.navigate("analyzes")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Карта пациента",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        GlideImage(
            modifier = Modifier
                .height(120.dp)
                .width(140.dp)
                .clip(CircleShape)
                .background(Color(0x80D9D9D9))
                .clickable(
                    onClick = {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT

                        resultLauncher.launch(intent)
                    }
                ),
            imageModel = image,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
            ),
            failure = {

            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Без карты пациента вы не сможете заказать анализы.",
            fontSize = 15.sp,
            color = Color(0xFF939396),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
            fontSize = 15.sp,
            color = Color(0xFF939396),
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = firstName,
            placeholder = { Text("Имя") },
            onValueChange = {
                firstName = it
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = patronymic,
            placeholder = { Text("Отчество") },
            onValueChange = {
                patronymic = it
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = lastName,
            placeholder = { Text("Фамилия") },
            onValueChange = {
                lastName = it
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = birthday,
            placeholder = { Text("Дата рождения") },
            onValueChange = {
                birthday = it
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = genderExpanded,
            onExpandedChange = {
                genderExpanded = it
            }
        ) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = gender,
                placeholder = { Text("Пол") },
                readOnly = true,
                onValueChange = {
                    gender = it
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_down),
                        contentDescription = "",
                        tint = Color(0xFF7E7E9A)
                    )
                    DropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                gender = "Не выбран"
                                genderExpanded = false
                            }
                        ) {
                            Text(text = "Не выбран")
                        }
                        DropdownMenuItem(
                            onClick = {
                                gender = "Мужской"
                                genderExpanded = false
                            }
                        ) {
                            Text(text = "Мужской")
                        }
                        DropdownMenuItem(
                            onClick = {
                                gender = "Женский"
                                genderExpanded = false
                            }
                        ) {
                            Text(text = "Женский")
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Сохранить",
            onClick = {
                if (token != null) {
                    viewModel.saveProfile(
                        name = firstName,
                        lastName = lastName,
                        patronymic = patronymic,
                        bith = birthday,
                        pol = gender,
                        token = token
                    )

                    if (imageUpdated) {
                        viewModel.uploadImage(
                            image!!,
                            token,
                            mContext.contentResolver
                        )
                    }
                }
            }
        )
    }
}
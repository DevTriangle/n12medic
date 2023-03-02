package com.triangle.n12medic.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic.common.ApiService
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    val isSuccess = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    @SuppressLint("CoroutineCreationDuringComposition")
    fun saveProfile(
        name: String,
        lastName: String,
        patronymic: String,
        bith: String,
        pol: String,
        token: String
    ) {
        isSuccess.value = null
        message.value = null

        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val profileInfo = mapOf(
                    "firstname" to name,
                    "lastname" to lastName,
                    "middlename" to patronymic,
                    "bith" to bith,
                    "pol" to pol,
                )
                val t = token.replace("\"", "")

                val json = apiService.saveProfile("Bearer $t", profileInfo)

                if (json.code() == 200) {
                    isSuccess.value = true
                } else if (json.code() == 403) {
                    isSuccess.value = false
                    message.value = "Вы не авторизованы"
                } else {
                    isSuccess.value = false
                    message.value = "Ошибка"
                }
            } catch (e: java.lang.Exception) {
                isSuccess.value = false
                message.value = e.message
            }
        }
    }
}
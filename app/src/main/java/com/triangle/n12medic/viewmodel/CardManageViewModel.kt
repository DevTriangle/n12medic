package com.triangle.n12medic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic.common.ApiService
import kotlinx.coroutines.launch

class CardManageViewModel: ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()

    fun createCard(
        name: String,
        lastName: String,
        patronymic: String, //middlename
        bith: String,
        pol: String,
        token: String
    ) {
        isSuccess.value = null
        errorMessage.value = null
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
                Log.d("Token", token)
                val t = token.replace("\"", "")
                Log.d("Token", t)

                val json = apiService.createProfile("Bearer $t", profileInfo)

                if (json.code() == 200) {
                    isSuccess.value = true
                } else if (json.code() == 403) {
                    isSuccess.value = false
                    errorMessage.value = "Вы не авторизованы"
                } else {
                    isSuccess.value = false
                    errorMessage.value = "Ошибка"
                }
            } catch (e: Exception) {
                isSuccess.value = false
                Log.d("err", e.toString())
                errorMessage.value = e.message
            }
        }
    }
}
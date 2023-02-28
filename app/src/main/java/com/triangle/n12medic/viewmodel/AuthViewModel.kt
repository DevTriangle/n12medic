package com.triangle.n12medic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic.common.ApiService
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    val message = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    val authToken = MutableLiveData<String>()
    val authErrorMessage = MutableLiveData<String>()

    fun sendCode(email: String) {
        errorMessage.value = null
        message.value = null

        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val json = apiService.sendEmailCode(email)

                Log.d("email", email)
                Log.d("json", json.code().toString())
                if (json.code() == 200) {
                    message.value = json.body()?.get("message").toString().replace("\"", "")
                } else if (json.code() == 422) {
                    message.value = json.body()?.get("errors").toString()
                } else {
                    errorMessage.value = "Ошибка"
                }
            } catch (e: Exception) {
                Log.d("err", e.toString())
                errorMessage.value = e.message
            }
        }
    }

    fun authUser(email: String, code: String) {
        authToken.value = null
        authErrorMessage.value = null

        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val headers: Map<String, String> = mapOf(
                    "email" to email,
                    "code" to code
                )
                val json = apiService.signIn(headers)

                if (json.code() == 200) {
                    authToken.value = json.body()?.get("token").toString()
                } else if (json.code() == 422) {
                    authErrorMessage.value = "Ошибка в логине или пароле"
                } else {
                    authErrorMessage.value = "Ошибка"
                }
            } catch (e: Exception) {
                Log.d("err", e.toString())
                authErrorMessage.value = e.message
            }
        }
    }
}
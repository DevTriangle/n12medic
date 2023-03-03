package com.triangle.n12medic.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic.common.ApiService
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.math.log

class ProfileViewModel: ViewModel() {
    val isSuccess = MutableLiveData<Boolean>()
    val isSuccessUploadImage = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    
    val uploadMessage = MutableLiveData<String>()

    val selectedImage = MutableLiveData<String>()
    fun setImage(imageUrl: String?) {
        if (imageUrl != null) {
            selectedImage.value = imageUrl
        }
    }

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
                    Log.d(TAG, "saveProfile: ${json.code()} - ${json.body()?.get("error")}")
                    isSuccess.value = false
                    message.value = "Ошибка"
                }
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "saveProfile: ${e.message}")
                isSuccess.value = false
                message.value = e.message
            }
        }
    }

    fun uploadImage(
        imageUrl: String,
        token: String,
        contentResolver: ContentResolver
    ) {
        message.value = null
        isSuccessUploadImage.value = null
        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val t = token.replace("\"", "")

                val file = File(imageUrl)

                Log.d(TAG, "file: ${file.path}")
                Log.d(TAG, "type: ${contentResolver.getType(imageUrl.toUri())}")

                val multipartBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.path)
                    .addFormDataPart("type", contentResolver.getType(imageUrl.toUri()))
                    .build()

                val json = apiService.uploadImage("Bearer $t", multipartBody.part(0), multipartBody.part(1))

                if (json.code() == 200) {
                    isSuccessUploadImage.value = true
                } else if (json.code() == 401) {
                    isSuccessUploadImage.value = false
                    message.value = "Вы не авторизованы"
                } else {
                    Log.d(TAG, "uploadImage: ${json.code()} - ${json.body()}")
                    isSuccessUploadImage.value = false
                    message.value = "Ошибка"
                }
            } catch (e: Exception) {
                Log.d(TAG, "uploadImage: $e")
                uploadMessage.value = e.message
            }
        }
    }
}
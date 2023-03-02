package com.triangle.n12medic.common

import com.google.gson.JsonObject
import com.triangle.n12medic.model.Analysis
import com.triangle.n12medic.model.News
import okhttp3.MultipartBody
import org.json.JSONArray
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*

interface ApiService {

    @Headers(
        "accept: application/json",
    )
    @POST("sendCode")
    suspend fun sendEmailCode(@Header("email") email: String) : Response<JsonObject> // Отправка кода авторизации на почту

    @Headers(
        "accept: application/json",
    )
    @POST("signin")
    suspend fun signIn(@HeaderMap headers: Map<String, String>) : Response<JsonObject> // Авторизация пользователя

    @Headers(
        "accept: application/json",
    )
    @POST("createProfile")
    suspend fun createProfile(@Header("Authorization") auth: String, @Body profileInfo: Map<String, String>) : Response<JsonObject> // Создание карты пользователя

    @Headers(
        "accept: application/json",
    )
    @PUT("updateProfile")
    suspend fun saveProfile(@Header("Authorization") auth: String, @Body profileInfo: Map<String, String>) : Response<JsonObject> // Сохранение карты пользователя

    @Multipart
    @Headers(
        "accept: */*",
        "Content-Type: multipart/form-data",
    )
    @POST("avatar")
    suspend fun uploadImage(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part
    ) : Response<JsonObject> // Изменение изображения профиля

    @Headers(
        "accept: application/json",
    )
    @GET("news")
    suspend fun loadNews() : List<News>

    @Headers(
        "accept: application/json",
    )
    @GET("catalog")
    suspend fun loadCatalog() : List<Analysis>

    companion object {
        var apiService: ApiService? = null

        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://medic.madskill.ru/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

            return apiService!!
        }
    }
}
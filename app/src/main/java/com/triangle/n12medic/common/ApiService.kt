package com.triangle.n12medic.common

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers(
        "accept: application/json",
    )
    @POST("sendCode")
    suspend fun sendEmailCode(@Header("email") email: String) : Response<JsonObject>

    @Headers(
        "accept: application/json",
    )
    @POST("signin")
    suspend fun signIn(@HeaderMap headers: Map<String, String>) : Response<JsonObject>

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
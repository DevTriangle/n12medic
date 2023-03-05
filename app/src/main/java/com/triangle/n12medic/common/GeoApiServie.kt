package com.triangle.n12medic.common

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GeoApiService {

    @Headers(
        "Content-type: application/json; charset=UTF-8",
    )
    @GET("reverse?format=json")
    suspend fun getLocationAddress(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): Response<JsonObject>

    companion object {
        var geoApiService: GeoApiService? = null

        fun getInstance(): GeoApiService {
            if (geoApiService == null) {
                geoApiService = Retrofit.Builder()
                    .baseUrl("https://nominatim.openstreetmap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GeoApiService::class.java)
            }

            return geoApiService!!
        }
    }
}
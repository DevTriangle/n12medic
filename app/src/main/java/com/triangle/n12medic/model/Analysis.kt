package com.triangle.n12medic.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Analysis(
    @SerializedName("id") val id: Double,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("category") val category: String,
    @SerializedName("time_result") val timeResult: String,
    @SerializedName("preparation") val preparation: String,
    @SerializedName("bio") val bio: String
)
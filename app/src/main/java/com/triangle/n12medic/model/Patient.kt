package com.triangle.n12medic.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Patient(
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("middlename") val middlename: String,
    @SerializedName("bith") val bith: String,
    @SerializedName("pol") val pol: String,
    @SerializedName("image") val image: String?,
)

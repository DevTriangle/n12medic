package com.triangle.n12medic.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Patient(
    @SerializedName("firstname") var firstName: String,
    @SerializedName("lastname") var lastName: String,
    @SerializedName("middlename") var middlename: String,
    @SerializedName("bith") var bith: String,
    @SerializedName("pol") var pol: String,
    @SerializedName("image") var image: String?,
    @SerializedName("cart") var cart: MutableList<CartItem>?
)

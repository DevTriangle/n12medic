package com.triangle.n12medic.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.triangle.n12medic.model.CartItem

class CartService() {
    fun loadCart(sharedPreferences: SharedPreferences): MutableList<CartItem> {
        val cart = ArrayList<CartItem>().toMutableList()
        val cartJson = Gson().fromJson(sharedPreferences.getString("cart", "[]"), JsonArray::class.java)

        cartJson.forEach { jsonElement ->
            val jsonObject = jsonElement.asJsonObject

            cart.add(CartItem(
                id = jsonObject.get("id").asInt,
                name = jsonObject.get("name").asString,
                price = jsonObject.get("price").asString,
                count = jsonObject.get("count").asInt,
            ))
        }

        return cart
    }

    fun saveCart(sharedPreferences: SharedPreferences, cart: MutableList<CartItem>) {
        val json = Gson().toJson(cart)

        with(sharedPreferences.edit()) {
            putString("cart", json)
            apply()
        }
    }
}
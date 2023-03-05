package com.triangle.n12medic.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.triangle.n12medic.common.GeoApiService
import kotlinx.coroutines.launch

class OrderViewModel:ViewModel() {
    val message = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    val lat = MutableLiveData<Double>()
    val lon = MutableLiveData<Double>()
    val alt = MutableLiveData<Double>()

    val isSuccessGetLoc = MutableLiveData<Boolean>()

    @SuppressLint("MissingPermission")
    fun getLocationAddress(locationService: FusedLocationProviderClient) {
        message.value = null
        address.value = null

        val apiService = GeoApiService.getInstance()

        locationService.lastLocation
            .addOnCompleteListener() { locationTask ->
                if (locationTask.isSuccessful) {
                    lat.value = locationTask.result.latitude
                    lon.value = locationTask.result.longitude
                    alt.value = locationTask.result.longitude

                    viewModelScope.launch {
                        try {
                            if (lat.value != null && lon.value != null) {
                                val json = apiService.getLocationAddress(lat.value!!, lon.value!!)

                                if (json.code() == 200) {
                                    address.value = json.body()?.get("display_name").toString()
                                } else {
                                    Log.d(TAG, "getLocationAddress: ${json.message()}")
                                    message.value = json.message()
                                }
                            }
                        } catch (e:Exception) {
                            Log.d(TAG, "getLocationAddress: $e")
                            message.value = e.message
                        }
                    }
                }
            }
    }
}
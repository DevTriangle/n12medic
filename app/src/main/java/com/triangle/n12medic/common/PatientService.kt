package com.triangle.n12medic.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.triangle.n12medic.model.Patient

class PatientService {
    fun loadPatientList(sharedPreferences: SharedPreferences): MutableList<Patient> {
        val patientList: MutableList<Patient> = ArrayList<Patient>().toMutableList()
        val patientsJson = Gson().fromJson(sharedPreferences.getString("patientList", "[]"), JsonArray::class.java)

        for (p in patientsJson) {
            val jsonObject = p.asJsonObject

            patientList.add(
                Patient(
                    jsonObject.get("firstname").asString,
                    jsonObject.get("lastname").asString,
                    jsonObject.get("middlename").asString,
                    jsonObject.get("bith").asString,
                    jsonObject.get("pol").asString,
                    jsonObject.get("image").asString,
                )
            )
        }

        return patientList
    }

    fun savePatientList(sharedPreferences: SharedPreferences, patientList: MutableList<Patient>) {
        val json = Gson().toJson(patientList)
        with(sharedPreferences.edit()) {
            putString("patientList", json)
            apply()
        }
    }
}
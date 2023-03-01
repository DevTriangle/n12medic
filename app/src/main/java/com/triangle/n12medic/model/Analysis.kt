package com.triangle.n12medic.model

data class Analysis(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val category: String,
    val timeResult: String,
    val preparation: String,
    val bio: String
)
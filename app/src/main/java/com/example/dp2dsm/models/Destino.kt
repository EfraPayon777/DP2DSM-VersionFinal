package com.example.dp2dsm.models


data class Destino(
    var id: String? = null,
    val nombre: String = "",
    val pais: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val imageUrl: String = "",
    val userId: String? = null
) {

    constructor() : this(null, "", "", 0.0, "", "", null)
}
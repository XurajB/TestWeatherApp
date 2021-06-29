package com.example.weathertest.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int,
    val speed: Double,
    val gust: Double
)
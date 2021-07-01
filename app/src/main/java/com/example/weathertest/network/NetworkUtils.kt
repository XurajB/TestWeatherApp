package com.example.weathertest.network

class NetworkUtils {
    fun getApiUrl(city: String): String {
        return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY"
    }

    companion object {
        const val API_KEY = "90ce288a450b0f32ccb5a692cccd4abc"
    }
}
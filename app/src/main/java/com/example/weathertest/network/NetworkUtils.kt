package com.example.weathertest.network

class NetworkUtils {
    companion object {
        fun getWeatherApiUrl(city: String): String {
            val API_KEY = "90ce288a450b0f32ccb5a692cccd4abc"
            return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY"
        }
    }
}
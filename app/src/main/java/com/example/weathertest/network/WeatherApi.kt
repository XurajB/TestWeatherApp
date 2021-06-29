package com.example.weathertest.network

import com.example.weathertest.model.WeatherResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class WeatherApi {

    private var client: OkHttpClient = OkHttpClient()

    fun getWeatherFromNetwork(url: String): WeatherResponse {
        val request: Request =
            Request.Builder().url(url)
                .build()
        val response = client.newCall(request).execute()
        val responseString = response.body!!.string()

        val serializedResponse = Json.decodeFromString<WeatherResponse>(responseString)

        return serializedResponse
    }
}
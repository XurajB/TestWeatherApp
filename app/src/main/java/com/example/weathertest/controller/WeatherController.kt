package com.example.weathertest.controller

import com.example.weathertest.model.WeatherResponse
import com.example.weathertest.network.WeatherApi
import com.example.weathertest.view.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherController {
    private var weatherView: WeatherView? = null
    private val weatherApi = WeatherApi() // dependency

    private val parentJob = Job()
    private val coroutineScope  = CoroutineScope(Dispatchers.IO + parentJob)

    fun initComponents(weatherView: WeatherView) { // method injection
        this.weatherView = weatherView
    }

    fun updateWeather(url: String) {
        coroutineScope.launch(Dispatchers.IO) {
            val serializedResponse = weatherApi.getWeatherFromNetworkAndDecode(url)
            coroutineScope.launch(Dispatchers.Main) {
                updateView(serializedResponse)
            }
        }
    }

    private fun updateView(weatherResponse: WeatherResponse) {
        weatherView?.let { wv ->
            wv.getCityView().text = weatherResponse.name
            wv.getTemperatureView().text = weatherResponse.main.temp.toString()
            wv.getDescriptionView().text = weatherResponse.weather[0].description
        }
    }

    fun onDestroy() {
        this.weatherView = null
    }
}
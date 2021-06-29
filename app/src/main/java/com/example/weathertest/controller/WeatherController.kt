package com.example.weathertest.controller

import com.example.weathertest.network.WeatherApi
import com.example.weathertest.view.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherController {
    private var weatherView: WeatherView? = null

    private val parentJob = Job()
    private val coroutineScope  = CoroutineScope(Dispatchers.IO + parentJob)

    fun initComponents(weatherView: WeatherView) {
        this.weatherView = weatherView
    }

    fun updateWeather(url: String) {
        coroutineScope.launch(Dispatchers.IO) {

            val weatherApi = WeatherApi()
            val serializedResponse = weatherApi.getWeatherFromNetwork(url)

            coroutineScope.launch(Dispatchers.Main) {
                weatherView?.let {
                    it.getCityView().text = serializedResponse.name
                    it.getTemperatureView().text = serializedResponse.main.temp.toString()
                    it.getDescriptionView().text = serializedResponse.weather[0].description
                }
            }
        }
    }

    fun onDestroy() {
        this.weatherView = null
    }
}
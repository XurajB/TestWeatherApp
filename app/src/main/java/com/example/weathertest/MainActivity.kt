package com.example.weathertest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertest.controller.WeatherController
import com.example.weathertest.network.NetworkUtils
import com.example.weathertest.view.WeatherView

class MainActivity : AppCompatActivity(), WeatherView {
    private lateinit var tempView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var cityView: TextView
    private val networkUtils = NetworkUtils()

    private lateinit var weatherController: WeatherController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val city = "Austin"
        val url = networkUtils.getApiUrl(city)

        cityView = findViewById(R.id.city)
        tempView = findViewById(R.id.temperature)
        descriptionView = findViewById(R.id.description)

        weatherController = WeatherController()
        weatherController.initComponents(this)
        weatherController.updateWeather(url)
    }

    override fun getDescriptionView(): TextView {
        return descriptionView
    }

    override fun getTemperatureView(): TextView {
        return tempView
    }

    override fun getCityView(): TextView {
        return cityView
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherController.onDestroy()
    }
}
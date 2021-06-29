package com.example.weathertest.view

import android.widget.TextView

interface WeatherView {
    fun getDescriptionView(): TextView
    fun getTemperatureView(): TextView
    fun getCityView(): TextView
}
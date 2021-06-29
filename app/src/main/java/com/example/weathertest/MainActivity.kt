package com.example.weathertest

import com.example.weathertest.model.WeatherResponse
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertest.controller.WeatherController
import com.example.weathertest.view.WeatherView
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity(), WeatherView {

    private lateinit var tempView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var cityView: TextView

    private lateinit var weatherController: WeatherController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val API_KEY = "90ce288a450b0f32ccb5a692cccd4abc"
        val cityString = "Austin"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityString&appid=$API_KEY"

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

    private fun getWeatherData(url: String) {
        val request: Request =
            Request.Builder().url(url)
                .build()
        val response = client.newCall(request).execute()
        val responseString = response.body!!.string()

        val serializedResponse = Json.decodeFromString<WeatherResponse>(responseString)

        val parentJob = Job()
        val coroutineScope  = CoroutineScope(Dispatchers.Main + parentJob)

        coroutineScope.launch(Dispatchers.Main) {
            val city = findViewById<TextView>(R.id.city)
            val temp = findViewById<TextView>(R.id.temperature)
            val description = findViewById<TextView>(R.id.description)

            city.text = serializedResponse.name
            temp.text = serializedResponse.main.temp.toString()
            description.text = serializedResponse.weather[0].description
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private var client: OkHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun run(url: String): String {
        val request: Request =
            Request.Builder().url(url)
                .build()
        client.newCall(request).execute().use {
                response -> return response.body!!.string()
        }
    }
}
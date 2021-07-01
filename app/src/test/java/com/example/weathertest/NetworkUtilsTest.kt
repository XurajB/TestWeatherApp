package com.example.weathertest

import com.example.weathertest.network.NetworkUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NetworkUtilsTest {

    private lateinit var networkUtils: NetworkUtils

    @Before
    fun before() {
        networkUtils = NetworkUtils()
    }

    @Test
    fun constructApiUrl_pass() {
        val url = networkUtils.getApiUrl("Austin")
        assertEquals(url, "https://api.openweathermap.org/data/2.5/weather?q=Austin&appid=90ce288a450b0f32ccb5a692cccd4abc")
    }

}
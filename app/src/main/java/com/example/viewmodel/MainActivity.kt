package com.example.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import data.ApiWeather
import data.Weather
import viewmodel.WeatherViewModel
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        val townToImport = findViewById<EditText>(R.id.editTextText)
        val confitmButton = findViewById<Button>(R.id.button)
        val townText = findViewById<TextView>(R.id.townView)
        val weatherText = findViewById<TextView>(R.id.weathweView)
        val discriptionText = findViewById<TextView>(R.id.discriptionView)
        val weatherImage = findViewById<ImageView>(R.id.imageView)

        val WeatherModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        confitmButton.setOnClickListener { WeatherModel.parsingFunction(townToImport.text.toString()) }

        WeatherModel.weatherData.observe(this, Observer<ApiWeather> { value: ApiWeather ->
            townText.text = value.city?.name
            weatherText.text = value.list?.get(0)?.main?.temp?.minus(272.15)
                ?.toBigDecimal()?.setScale(2, RoundingMode.UP).toString()
            discriptionText.text = (value.list?.get(0)?.weather?.get(0)?.description)
            val icnURL =
                "https://openweathermap.org/img/w/" + (value.list?.get(0)?.weather?.get(0)?.icon) + ".png"
            Picasso.get().load(icnURL).into(weatherImage)
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val townText = findViewById<EditText>(R.id.editTextText)
        outState.putString("Town", townText.text.toString())
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val townText = findViewById<EditText>(R.id.editTextText)
        val townName: String = savedInstanceState.getString("Town").toString()
        townText.setText(townName)
    }
}

package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.ApiWeather
import data.RetrofitWeatherApi
import data.TownRetroApi
import data.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel() : ViewModel(){
    var weatherData : MutableLiveData<ApiWeather> = MutableLiveData()
    fun parsingFunction(TownName: String) {
        var result: ApiWeather = ApiWeather(null, null, null, null, null)
        val retrofitWeather = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val productApi = retrofitWeather.create(TownRetroApi::class.java)

        val parsCorutin: Deferred<ApiWeather> = CoroutineScope(Dispatchers.Default).async {
            val Towns = productApi.getTowns(TownName, "a4fe33dad143868bda7137465545a56d")
            val weather = retrofitWeather.create(RetrofitWeatherApi::class.java)
            val weatherResult =
                weather.getWeather(Towns[0].lat, Towns[0].lon, "a4fe33dad143868bda7137465545a56d")

            result = weatherResult
            return@async result
        }

        runBlocking {
            val SecondResult = parsCorutin.await()
            weatherData.value = SecondResult
            return@runBlocking SecondResult
        }
    }
}

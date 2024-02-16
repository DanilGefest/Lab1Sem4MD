package data

import retrofit2.http.GET
import retrofit2.http.Query

data class Towns(
    val langCode: String? = null,
)

data class TownData(
    val name: String,
    val localNames: Array<String>? = null,
    val lat: Double,
    val lon: Double,
    val country: String? = null,
    val state: String? = null
)

data class Main(
    var temp: Double,
    val feels_like: String? = null,
    val temp_min: String? = null,
    val temp_max: String? = null,
    val pressure: String? = null,
    val sea_level: String? = null,
    val grnd_level: String? = null,
    val humidity: String? = null,
    val temp_kf: String? = null,
)

data class Weather(
    val id: String? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null,
)

data class Clouds(
    val all: String? = null,
)

data class Wind(
    val speed: String? = null,
    val deg: String? = null,
    val gust: String? = null,
)

data class Rain(
    val h3: String? = null,
)

data class Sys(
    val pod: String? = null,
)

data class coordinates(
    val lat: String? = null,
    val lon: String? = null,
)

data class Town(
    val id: String? = null,
    val name: String? = null,
    val coord: coordinates? = null,
    val country: String? = null,
    val population: String? = null,
    val timezone: String? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
)

data class List(
    val dt: String? = null,
    val main: Main,
    val weather: Array<Weather>? = null,
    val clouds: Clouds? = null,
    val wind: Wind? = null,
    val visibility: String? = null,
    val pop: String? = null,
    val rain: Rain? = null,
    val sys: Sys? = null,
    val dt_txt: String? = null,
)

data class ApiWeather(
    val cod: String? = null,
    val message: String? = null,
    val cnt: String? = null,
    val list: Array<List>? = null,
    val city: Town? = null,
)

interface TownRetroApi {
    @GET("/geo/1.0/direct?")
    suspend fun getTowns(
        @Query("q") Town: String,
        @Query("appid") appid: String): Array<TownData>
}

interface RetrofitWeatherApi {
    @GET("https://api.openweathermap.org/data/2.5/forecast?")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String): ApiWeather
}


package fr.cvlaminck.hwweather.core.managers

import android.content.ContentResolver
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import javax.inject.Inject

public class WeatherManager @Inject constructor(

) {

    fun getWeatherForCity(city: CityEntity) {
        val data = WeatherData();

    }

    private fun refreshWeatherDataFromCache(): WeatherData {

    }


}
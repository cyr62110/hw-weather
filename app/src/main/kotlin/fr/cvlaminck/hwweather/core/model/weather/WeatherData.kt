package fr.cvlaminck.hwweather.core.model.weather

import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity

class WeatherData (
        val city: CityEntity,
        val daily: MutableList<DailyForecastEntity> = arrayListOf()
) {
}
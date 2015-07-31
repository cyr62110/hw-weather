package fr.cvlaminck.hwweather.data.model

import fr.cvlaminck.hwweather.model.WeatherCondition
import java.util.*

@data class DailyForecastEntity(
        val day: Date,
        val city: CityEntity,
        val condition: WeatherCondition,
        val temperatureMin: Double,
        val temperatureMax: Double
) {

}
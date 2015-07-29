package fr.cvlaminck.hwweather.data.model

import fr.cvlaminck.hwweather.model.WeatherCondition
import java.util.*

@data class DailyForecast(
        val day: Date,
        val condition: WeatherCondition,
        val temperatureMin: Double,
        val temperatureMax: Double
) {

}
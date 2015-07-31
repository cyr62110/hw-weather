package fr.cvlaminck.hwweather.data.model

import fr.cvlaminck.hwweather.model.WeatherCondition
import java.util.Date

public class HourlyForecastEntity(
        val hour: Date,
        val city: CityEntity,
        val condition: WeatherCondition,
        val temperature: Double
) {
}
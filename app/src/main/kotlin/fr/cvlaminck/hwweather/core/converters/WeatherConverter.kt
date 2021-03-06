package fr.cvlaminck.hwweather.core.converters

import fr.cvlaminck.hwweather.client.resources.weather.CurrentWeatherResource
import fr.cvlaminck.hwweather.client.resources.weather.DailyForecastResource
import fr.cvlaminck.hwweather.client.resources.weather.HourlyForecastResource
import fr.cvlaminck.hwweather.client.resources.weather.WeatherConditionResource
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.WeatherCondition
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import javax.inject.Inject

public class WeatherConverter public @Inject constructor() {

    fun convert(condition: WeatherConditionResource): WeatherCondition {
        return WeatherCondition.CLEAR; //FIXME once the format of the resource is defined
    }

    fun convert(daily: DailyForecastResource): DailyForecastEntity {
        val entity = DailyForecastEntity();
        entity.day = LocalDateTime(daily.date);
        entity.maxTemperatureInCelsius = daily.maxTemperatureInCelsius;
        entity.minTemperatureInCelsius = daily.minTemperatureInCelsius;
        entity.condition = convert(daily.weatherCondition);
        return entity;
    }

    fun convert(hourly: HourlyForecastResource): HourlyForecastEntity {
        val entity = HourlyForecastEntity();
        entity.hour = LocalDateTime(hourly.date);
        entity.temperature = hourly.temperatureInCelsius;
        entity.condition = convert(hourly.weatherCondition);
        return entity;
    }

    fun convert(current: CurrentWeatherResource): CurrentWeatherEntity {
        val entity = CurrentWeatherEntity();
        entity.temperature = current.temperatureInCelsius;
        entity.condition = convert(current.weatherCondition);
        return entity;
    }
}
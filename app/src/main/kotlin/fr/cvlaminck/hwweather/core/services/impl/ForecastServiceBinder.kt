package fr.cvlaminck.hwweather.core.services.impl

import android.os.Binder
import fr.cvlaminck.hwweather.core.services.ForecastService
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.data.model.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity
import fr.cvlaminck.hwweather.model.WeatherEntity

public class ForecastServiceBinder(private val forecastService: ForecastServiceImpl) : Binder(), ForecastService {
    override fun getWeatherForCity(city: CityEntity): WeatherEntity = forecastService.getWeatherForCity(city);
    override fun getDailyForecastsForCity(city: CityEntity): Collection<DailyForecastEntity> = forecastService.getDailyForecastsForCity(city);
    override fun getHourlyForecastsForCity(city: CityEntity): Collection<HourlyForecastEntity> = forecastService.getHourlyForecastsForCity(city);
}
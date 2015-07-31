package fr.cvlaminck.hwweather.core.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.data.model.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity
import fr.cvlaminck.hwweather.model.WeatherEntity

interface ForecastService {
    fun getWeatherForCity(city: CityEntity): WeatherEntity;

    fun getDailyForecastsForCity(city: CityEntity): Collection<DailyForecastEntity>;

    fun getHourlyForecastsForCity(city: CityEntity): Collection<HourlyForecastEntity>;
}
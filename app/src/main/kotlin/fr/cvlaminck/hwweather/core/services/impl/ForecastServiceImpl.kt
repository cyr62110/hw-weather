package fr.cvlaminck.hwweather.core.services.impl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import fr.cvlaminck.hwweather.core.services.ForecastService
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.data.model.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity
import fr.cvlaminck.hwweather.model.WeatherEntity

public class ForecastServiceImpl : Service(), ForecastService {
    private val binder = ForecastServiceBinder(this);

    override fun onBind(intent: Intent): IBinder? = binder;

    override fun getWeatherForCity(city: CityEntity): WeatherEntity {
        throw UnsupportedOperationException()
    }

    override fun getDailyForecastsForCity(city: CityEntity): Collection<DailyForecastEntity> {
        throw UnsupportedOperationException()
    }

    override fun getHourlyForecastsForCity(city: CityEntity): Collection<HourlyForecastEntity> {
        return listOf<HourlyForecastEntity>();
    }
}
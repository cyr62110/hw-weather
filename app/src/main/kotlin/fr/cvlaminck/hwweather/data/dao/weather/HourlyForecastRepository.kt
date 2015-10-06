package fr.cvlaminck.hwweather.data.dao.weather

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity

public class HourlyForecastRepository(
        connectionSource: ConnectionSource,
        clazz: Class<HourlyForecastEntity>
) : BaseDaoImpl<HourlyForecastEntity, Int> (connectionSource, clazz) {

}
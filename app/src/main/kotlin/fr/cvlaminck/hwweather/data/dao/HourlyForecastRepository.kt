package fr.cvlaminck.hwweather.data.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity

public class HourlyForecastRepository(
        connectionSource: ConnectionSource,
        clazz: Class<HourlyForecastEntity>
) : BaseDaoImpl<HourlyForecastEntity, Int> (connectionSource, clazz) {

}
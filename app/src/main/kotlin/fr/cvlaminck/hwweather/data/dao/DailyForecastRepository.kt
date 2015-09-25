package fr.cvlaminck.hwweather.data.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.DailyForecastEntity

public class DailyForecastRepository (
        connectionSource: ConnectionSource,
        clazz: Class<DailyForecastEntity>
) : BaseDaoImpl<DailyForecastEntity, Int>(connectionSource, clazz) {



}
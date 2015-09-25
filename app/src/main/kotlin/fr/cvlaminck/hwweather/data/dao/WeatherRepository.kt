package fr.cvlaminck.hwweather.data.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.WeatherEntity

public class WeatherRepository (
        connectionSource: ConnectionSource,
        clazz: Class<WeatherEntity>
)
: BaseDaoImpl<WeatherEntity, Int> (connectionSource, clazz){

}
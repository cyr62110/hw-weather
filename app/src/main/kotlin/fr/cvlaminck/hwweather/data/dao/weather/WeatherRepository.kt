package fr.cvlaminck.hwweather.data.dao.weather

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.weather.WeatherEntity

public class WeatherRepository (
        connectionSource: ConnectionSource,
        clazz: Class<WeatherEntity>
)
: BaseDaoImpl<WeatherEntity, Int> (connectionSource, clazz){

}
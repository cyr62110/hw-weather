package fr.cvlaminck.hwweather.data.dao.weather

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import org.joda.time.DateTime

public class CurrentWeatherRepository(
        connectionSource: ConnectionSource,
        clazz: Class<CurrentWeatherEntity>
): BaseDaoImpl<CurrentWeatherEntity, Int> (connectionSource, clazz){

    fun findByCity(city: CityEntity): CurrentWeatherEntity? {
        return queryBuilder().where()
            .eq("city_id", city.id)
            .queryForFirst();
    }

    fun deleteByCity(city: CityEntity) {
        val deleteBuilder = deleteBuilder();
        deleteBuilder.where().eq("city_id", city.id);
        deleteBuilder.delete();
    }

}
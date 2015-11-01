package fr.cvlaminck.hwweather.data.dao.weather

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

public class HourlyForecastRepository(
        connectionSource: ConnectionSource,
        clazz: Class<HourlyForecastEntity>
) : BaseDaoImpl<HourlyForecastEntity, Int> (connectionSource, clazz) {

    fun findByCityAndDateBetween(city: CityEntity, start: LocalDateTime, end: LocalDateTime): List<HourlyForecastEntity> {
        return queryBuilder().where()
                .eq("city_id", city.id).and()
                .ge("hour", start).and()
                .lt("hour", end)
                .query();
    }

    fun deleteByCityAndHourBetween(city: CityEntity, start: LocalDateTime, end: LocalDateTime) {
        val deleteBuilder = deleteBuilder();
        deleteBuilder.where()
                .eq("city_id", city.id).and()
                .ge("hour", start).and()
                .lt("hour", end);
        deleteBuilder.delete();
    }

}
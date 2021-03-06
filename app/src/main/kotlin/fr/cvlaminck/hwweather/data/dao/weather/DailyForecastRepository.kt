package fr.cvlaminck.hwweather.data.dao.weather

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.stmt.QueryBuilder
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.util.*

public class DailyForecastRepository (
        connectionSource: ConnectionSource,
        clazz: Class<DailyForecastEntity>
) : BaseDaoImpl<DailyForecastEntity, Int>(connectionSource, clazz) {

    fun findByCityAndDateBetween(city: CityEntity, start: LocalDateTime, end: LocalDateTime): List<DailyForecastEntity> {
        return queryBuilder().where()
                .eq("city_id", city.id).and()
                .ge("day", start).and()
                .lt("day", end)
                .query();
    }

    fun deleteByCityAndDayBetween(city: CityEntity, start: LocalDateTime, end: LocalDateTime) {
        val deleteBuilder = deleteBuilder();
        deleteBuilder.where()
                .eq("city_id", city.id).and()
                .ge("day", start).and()
                .lt("day", end);
        deleteBuilder.delete();
    }

}
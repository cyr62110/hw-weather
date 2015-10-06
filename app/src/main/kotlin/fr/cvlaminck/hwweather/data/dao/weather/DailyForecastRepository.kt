package fr.cvlaminck.hwweather.data.dao.weather

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.stmt.QueryBuilder
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import java.util.*

public class DailyForecastRepository (
        connectionSource: ConnectionSource,
        clazz: Class<DailyForecastEntity>
) : BaseDaoImpl<DailyForecastEntity, Int>(connectionSource, clazz) {

    fun findByCityAndDateBetween(city: CityEntity, start: Date, end: Date): List<DailyForecastEntity> {
        return queryBuilder().where()
                .eq("city", city.id).and()
                .ge("date", start).and()
                .lt("date", end)
                .query();
    }

}
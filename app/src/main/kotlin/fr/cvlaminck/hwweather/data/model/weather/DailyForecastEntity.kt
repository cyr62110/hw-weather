package fr.cvlaminck.hwweather.data.model.weather

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.DailyForecastRepository
import fr.cvlaminck.hwweather.data.model.Cacheable
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import java.util.Date

@DatabaseTable(tableName = "daily", daoClass = DailyForecastRepository::class)
class DailyForecastEntity : Cacheable {
    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(index = true)
    var day: Date = Date();

    @DatabaseField(foreign = true, index = true)
    var city: CityEntity? = null;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    var temperatureMin: Double = 0.0;

    @DatabaseField
    var temperatureMax: Double = 0.0;

    @DatabaseField
    override var cacheTimestamp: Long = System.currentTimeMillis();

    @DatabaseField
    override var expiryInSecond: Int = Cacheable.ONE_HOUR;

    @DatabaseField
    override var gracePeriodInSecond: Int = Cacheable.HALF_A_HOUR;
}
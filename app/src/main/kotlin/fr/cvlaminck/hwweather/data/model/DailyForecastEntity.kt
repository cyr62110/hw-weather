package fr.cvlaminck.hwweather.data.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.DailyForecastRepository
import java.util.Date

@DatabaseTable(daoClass = DailyForecastRepository::class)
class DailyForecastEntity : Cacheable {
    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField
    var day: Date = Date();

    @DatabaseField(foreign = true)
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
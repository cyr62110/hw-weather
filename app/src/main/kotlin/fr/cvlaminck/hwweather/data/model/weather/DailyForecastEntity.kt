package fr.cvlaminck.hwweather.data.model.weather

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.DailyForecastRepository
import fr.cvlaminck.hwweather.data.model.Cacheable
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import org.joda.time.DateTime
import java.util.Date

@DatabaseTable(tableName = "daily", daoClass = DailyForecastRepository::class)
public class DailyForecastEntity : Cacheable {
    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(index = true, uniqueCombo = true)
    var day: DateTime? = null;

    @DatabaseField(foreign = true, index = true, uniqueCombo = true)
    var city: CityEntity? = null;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    var minTemperatureInCelsius: Double = 0.0;

    @DatabaseField
    var maxTemperatureInCelsius: Double = 0.0;

    @DatabaseField
    override var cacheDate: DateTime = DateTime.now();

    @DatabaseField
    override var expiryInSecond: Int = Cacheable.ONE_HOUR;
}
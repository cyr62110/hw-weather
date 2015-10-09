package fr.cvlaminck.hwweather.data.model.weather

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.HourlyForecastRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import org.joda.time.DateTime
import java.util.Date

@DatabaseTable(tableName = "hourly", daoClass = HourlyForecastRepository::class)
public class HourlyForecastEntity {

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(index = true, uniqueCombo = true)
    var hour: DateTime? = null;

    @DatabaseField(foreign = true, index = true, uniqueCombo = true)
    var city: CityEntity? = null;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    var temperature: Double = 0.0;
}
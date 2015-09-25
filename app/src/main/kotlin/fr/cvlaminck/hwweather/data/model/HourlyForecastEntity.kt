package fr.cvlaminck.hwweather.data.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.HourlyForecastRepository
import java.util.Date

@DatabaseTable(daoClass = HourlyForecastRepository::class)
public class HourlyForecastEntity {

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField
    var hour: Date = Date();

    @DatabaseField(foreign = true)
    var city: CityEntity? = null;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

    @DatabaseField
    var temperature: Double = 0.0;
}
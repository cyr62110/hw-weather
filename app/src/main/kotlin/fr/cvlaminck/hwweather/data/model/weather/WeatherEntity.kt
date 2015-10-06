package fr.cvlaminck.hwweather.data.model.weather

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.weather.WeatherRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity

@DatabaseTable(tableName = "current", daoClass = WeatherRepository::class)
public class WeatherEntity {

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(foreign = true)
    var city: CityEntity? = null;

    @DatabaseField
    var temperature: Double = 0.0;

    @DatabaseField
    var condition: WeatherCondition = WeatherCondition.CLEAR;

}
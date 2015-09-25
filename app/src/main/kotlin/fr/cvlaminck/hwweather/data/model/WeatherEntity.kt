package fr.cvlaminck.hwweather.data.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.WeatherRepository

@DatabaseTable(daoClass = WeatherRepository::class)
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
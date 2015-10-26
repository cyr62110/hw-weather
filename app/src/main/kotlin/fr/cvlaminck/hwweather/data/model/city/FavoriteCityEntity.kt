package fr.cvlaminck.hwweather.data.model.city

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "favorite")
public class FavoriteCityEntity {

    @DatabaseField(id = true, foreign = true, foreignAutoRefresh = true)
    var city: CityEntity? = null;

    @DatabaseField
    var order: Int = 1;

}
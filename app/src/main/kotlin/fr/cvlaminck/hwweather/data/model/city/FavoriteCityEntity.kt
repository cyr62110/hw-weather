package fr.cvlaminck.hwweather.data.model.city

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.city.FavoriteCityRepository

@DatabaseTable(tableName = "favorite", daoClass = FavoriteCityRepository::class)
public class FavoriteCityEntity {

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var city: CityEntity? = null;

    @DatabaseField
    var order: Int = 1;

}
package fr.cvlaminck.hwweather.data.model.city

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "external_city_id")
class ExternalCityIdEntity {

    @DatabaseField(foreign = true)
    var city: CityEntity? = null;

    @DatabaseField(id = true)
    var provider: String? = null;

    @DatabaseField(id = true)
    var externalId: String? = null;

}
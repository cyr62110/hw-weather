package fr.cvlaminck.hwweather.data.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.CityRepository

@DatabaseTable(daoClass = CityRepository::class)
public class CityEntity : Cacheable {

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField
    var name: String? = null;

    @DatabaseField
    var county: String? = null;

    @DatabaseField
    var state: String? = null;

    @DatabaseField
    var country: String? = null;

    @DatabaseField
    override var cacheTimestamp: Long = System.currentTimeMillis();

    @DatabaseField
    override var expiryInSecond: Int = Cacheable.ONE_HOUR;

    @DatabaseField
    override var gracePeriodInSecond: Int = Cacheable.HALF_A_HOUR;
}
package fr.cvlaminck.hwweather.data.dao.city

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import javax.inject.Inject

public class CityRepository (
        connectionSource: ConnectionSource,
        entity: Class<CityEntity>
) : BaseDaoImpl<CityEntity, Int> (connectionSource, entity) {

}
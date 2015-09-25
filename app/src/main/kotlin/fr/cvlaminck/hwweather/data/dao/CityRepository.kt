package fr.cvlaminck.hwweather.data.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.CityEntity
import javax.inject.Inject

public class CityRepository (
        connectionSource: ConnectionSource,
        entity: Class<CityEntity>
) : BaseDaoImpl<CityEntity, Int> (connectionSource, entity) {
}
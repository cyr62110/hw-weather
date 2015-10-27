package fr.cvlaminck.hwweather.data.dao.city

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.FavoriteCityEntity

class FavoriteCityRepository(
        connectionSource: ConnectionSource,
        entity: Class<FavoriteCityEntity>
) : BaseDaoImpl<FavoriteCityEntity, Int> (connectionSource, entity) {

    fun findFavoritesOrdered(): List<FavoriteCityEntity> {
        return queryBuilder()
            .orderBy("order", true)
            .query();
    }

}
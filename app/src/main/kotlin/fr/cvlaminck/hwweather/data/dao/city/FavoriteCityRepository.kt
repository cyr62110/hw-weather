package fr.cvlaminck.hwweather.data.dao.city

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.FavoriteCityEntity

class FavoriteCityRepository(
        connectionSource: ConnectionSource,
        entity: Class<FavoriteCityEntity>
) : BaseDaoImpl<FavoriteCityEntity, Int> (connectionSource, entity) {

    fun findFavoritesOrdered(): List<CityEntity> {
        val favoriteCitiesOrdered = queryBuilder()
            .orderBy("order", true)
            .query();

        return favoriteCitiesOrdered
            .map { it.city as CityEntity };
    }

}
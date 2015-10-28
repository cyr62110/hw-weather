package fr.cvlaminck.hwweather.data.dao.city

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import fr.cvlaminck.hwweather.data.model.city.FavoriteCityEntity

class FavoriteCityRepository(
        connectionSource: ConnectionSource,
        entity: Class<FavoriteCityEntity>
) : BaseDaoImpl<FavoriteCityEntity, Int> (connectionSource, entity) {

    private val externalIdRepository = DaoManager.createDao(connectionSource, ExternalCityIdEntity::class.java) as ExternalCityIdRepository;

    fun findFavoritesOrdered(): List<FavoriteCityEntity> {
        return queryBuilder()
            .orderBy("order", true)
            .query();
    }

    fun isFavorite(city: CityEntity): Boolean {
        val cityId = if (city.id == null && city.serverExternalId != null) {
            externalIdRepository.findByProviderAndExternalId(
                    city.serverExternalId!!.provider as String,
                    city.serverExternalId!!.externalId as String)?.city?.id;
        } else if (city.id != null) {
            city.id;
        } else {
            null;
        }

        if (cityId == null) {
            return false;
        }
        return queryBuilder().where()
                .eq("city_id", cityId)
                .countOf() > 0;
    }

}
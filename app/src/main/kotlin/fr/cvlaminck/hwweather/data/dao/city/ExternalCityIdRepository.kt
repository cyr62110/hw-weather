package fr.cvlaminck.hwweather.data.dao.city

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity

public class ExternalCityIdRepository (
        connectionSource: ConnectionSource,
        entity: Class<ExternalCityIdEntity>
) : BaseDaoImpl<ExternalCityIdEntity, Int> (connectionSource, entity) {

    fun findByProviderAndExternalId(provider: String, externalId: String): ExternalCityIdEntity? {
        return queryBuilder().where()
                .eq("provider", provider).and()
                .eq("externalId", externalId)
                .queryForFirst();
    }

}
package fr.cvlaminck.hwweather.core.converters

import fr.cvlaminck.hwweather.client.resources.CityResource
import fr.cvlaminck.hwweather.client.resources.ExternalCityIdResource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import javax.inject.Inject

public class CityConverter public @Inject constructor() {

    fun convert(city: CityResource): CityEntity {
        val entity = CityEntity();
        entity.serverId = city.id;
        if (city.externalId != null) {
            entity.serverExternalId = convert(city.externalId);
            entity.serverExternalId?.city = entity;
        }
        entity.name = city.name;
        entity.country = city.country;
        return entity;
    }

    fun convert(externalId: ExternalCityIdResource): ExternalCityIdEntity {
        val entity = ExternalCityIdEntity();
        entity.provider = externalId.provider;
        entity.externalId = externalId.id;
        return entity;
    }

    fun convert(resources: Collection<CityResource>): Collection<CityEntity> {
        return resources.map {
            resource -> convert(resource);
        }
    }

    fun convert(externalId: ExternalCityIdEntity): ExternalCityIdResource {
        val resource = ExternalCityIdResource();
        resource.provider = externalId.provider;
        resource.id = externalId.externalId;
        return resource;
    }
}
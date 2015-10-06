package fr.cvlaminck.hwweather.core.converters

import fr.cvlaminck.hwweather.client.resources.CityResource
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import javax.inject.Inject

public class CityConverter public @Inject constructor() {

    fun convert(city: CityResource): CityEntity {
        val entity = CityEntity();
        entity.serverId = city.id;
        if (entity.id == null) {
            entity.serverId = city.externalId?.toString();
        }
        entity.name = city.name;
        entity.country = city.country;
        return entity;
    }

    fun convert(resources: Collection<CityResource>): Collection<CityEntity> {
        return resources.map {
            resource -> convert(resource);
        }
    }
}
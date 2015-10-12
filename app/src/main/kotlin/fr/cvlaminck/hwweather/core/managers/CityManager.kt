package fr.cvlaminck.hwweather.core.managers

import android.content.Context
import fr.cvlaminck.hwweather.client.HwWeatherClient
import fr.cvlaminck.hwweather.core.converters.CityConverter
import fr.cvlaminck.hwweather.core.loaders.city.SearchCityLoader
import fr.cvlaminck.hwweather.core.model.Page
import fr.cvlaminck.hwweather.data.dao.city.CityRepository
import fr.cvlaminck.hwweather.data.dao.city.ExternalCityIdRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import javax.inject.Inject

public class CityManager @Inject constructor(
        private val cityRepository: CityRepository,
        private val externalCityIdRepository: ExternalCityIdRepository,
        private val cityConverter: CityConverter,
        private val hwWeatherClient: HwWeatherClient
) {

    fun getCities(): List<CityEntity> = cityRepository.queryForAll();

    fun findCityByExternalId(externalId: ExternalCityIdEntity): CityEntity? {
        val dbExternalId = externalCityIdRepository.findByProviderAndExternalId(externalId.provider as String, externalId.externalId as String);
        dbExternalId?.city?.serverExternalId = dbExternalId;
        return dbExternalId?.city;
    }

    fun save(city: CityEntity): CityEntity {
        val dbCity = cityRepository.createIfNotExists(city);
        if (dbCity.serverExternalId != null) {
            dbCity.serverExternalId?.city = dbCity;
            dbCity.serverExternalId = externalCityIdRepository.createIfNotExists(dbCity.serverExternalId);
        }
        return dbCity;
    }

    fun createLoaderForSearch(context: Context, query: String) = SearchCityLoader(context, query, this);

    fun search(query: String): Page<CityEntity> {
        val response = hwWeatherClient.cities().search(query);
        val result = Page(
                response.numberOfResultPerPage,
                response.page,
                response.totalNumberOfResult,
                cityConverter.convert(response.results)
        );
        return result;
    }
}
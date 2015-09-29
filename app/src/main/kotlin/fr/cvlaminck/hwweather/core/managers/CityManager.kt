package fr.cvlaminck.hwweather.core.managers

import android.content.AsyncTaskLoader
import android.content.Context
import android.content.Loader
import android.os.OperationCanceledException
import fr.cvlaminck.hwweather.client.HwWeatherClient
import fr.cvlaminck.hwweather.client.exceptions.HwWeatherRequestException
import fr.cvlaminck.hwweather.client.resources.CityResource
import fr.cvlaminck.hwweather.core.model.Page
import fr.cvlaminck.hwweather.data.dao.CityRepository
import fr.cvlaminck.hwweather.data.model.CityEntity
import java.io.IOException
import java.util.*
import javax.inject.Inject

public class CityManager @Inject constructor(
        private val cityRepository: CityRepository,
        private val hwWeatherClient: HwWeatherClient
) {

    fun getCities(): List<CityEntity> = cityRepository.queryForAll();

    fun search(context: Context, city: String): Any =  object: AsyncTaskLoader<Page<CityEntity>>(context) {
        override fun loadInBackground(): Page<CityEntity>? {
            return try {
                val response = hwWeatherClient.cities().search(city);
                val page = Page<CityEntity>(
                        response.numberOfResultPerPage,
                        response.page,
                        response.totalNumberOfResult,

                )
                return null;
            } catch (e: Exception) {
                throw OperationCanceledException();
            }
        }
    }

    fun asCity(city: CityResource) : CityEntity {
        val entity = CityEntity();
        entity.serverId = city.id;
        if (entity.id == null) {
            entity.serverId = city.externalId?.toString();
        }
        entity.name = city.name;
        entity.country = city.country;
        return entity;
    }

    fun asCities(resources: Collection<CityResource>): Collection<CityEntity> {
        val cities = ArrayList<CityEntity>();
        return cities; //FIXME
    }
}
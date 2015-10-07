package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import javax.inject.Inject

public class WeatherManager @Inject constructor(
        private val cityManager: CityManager
) {

    fun getWeatherForCity(city: CityEntity, typesToRefresh: Collection<WeatherDataType>): WeatherData {
        var dbCity: CityEntity? = city;
        var data: WeatherData = WeatherData();
        // First, we check if the city is in our database.
        if (city.id != null) {
            dbCity = city;
        } else if (city.serverExternalId != null) {
            dbCity = cityManager.findCityByExternalId(city.serverExternalId as ExternalCityIdEntity);
        } else {
            //FIXME throw an illegal argument exception
        }
        // If not there is no need to try to refresh the data using the database.
        if (dbCity != null) {
            refreshWeatherDataFromCache(dbCity, data, typesToRefresh);
        }
        // Recompute types to refresh once data loaded from the database
        //FIXME
        // Refresh the missing values using the backend
        //FIXME
        // If the city was not in the database, we save it and we associate its external id
        //FIXME
        data.city = dbCity;
        return data;
    }

    private fun refreshWeatherDataFromCache(city: CityEntity, data: WeatherData, typesToRefresh: Collection<WeatherDataType>) {
        typesToRefresh.forEach { type ->
            when (type) {
                WeatherDataType.DAILY -> refreshDailyFromCache(city, data);
            //FIXME add other types
            }
        }
    }

    private fun refreshDailyFromCache(city: CityEntity, data: WeatherData) {
        //FIXME
    }

    private fun refreshWeatherDataUsingBackend(city: CityEntity, data: WeatherData, typesToRefresh: Collection<WeatherDataType>) {
        typesToRefresh.forEach { type ->
            when (type) {
                WeatherDataType.DAILY -> refreshDailyFromCache(city, data);
            //FIXME add other types
            }
        }
    }

    private fun refreshDailyUsingBackend(city: CityEntity, data: WeatherData) {
        //FIXME
    }
}
package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.client.HwWeatherClient
import fr.cvlaminck.hwweather.client.reponses.weather.WeatherResponse
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.core.converters.CityConverter
import fr.cvlaminck.hwweather.core.converters.WeatherConverter
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.dao.weather.DailyForecastRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject

public class WeatherManager @Inject constructor(
        private val dailyForecastRepository: DailyForecastRepository,
        private val cityManager: CityManager,
        private val cityConverter: CityConverter,
        private val weatherConverter: WeatherConverter,
        private val hwWeatherClient: HwWeatherClient
) {
    val numberOfDaily = 7; //TODO: Read the value from a configuration and maybe controlled by server.
    val numberOfHourly = 24;

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
        val typesToRefreshUsingBackend = updateTypesToRefresh(data, typesToRefresh);
        // Refresh the missing values using the backend
        if (typesToRefreshUsingBackend.isNotEmpty()) {
            refreshWeatherDataUsingBackend(city, dbCity, data, typesToRefreshUsingBackend);
        }
        return data;
    }

    private fun refreshWeatherDataFromCache(city: CityEntity, data: WeatherData, typesToRefresh: Collection<WeatherDataType>) {
        data.city = city;
        typesToRefresh.forEach { type ->
            when (type) {
                WeatherDataType.DAILY -> refreshDailyFromCache(city, data);
            //FIXME add other types
            }
        }
    }

    private fun refreshDailyFromCache(city: CityEntity, data: WeatherData) {
        val startDate = DateTime.now(DateTimeZone.UTC);
        val endDate = startDate.plusDays(numberOfDaily);

        val daily = dailyForecastRepository.findByCityAndDateBetween(city, startDate, endDate)
                .filter { daily -> !daily.expired };
        if (daily.size() == numberOfDaily) {
            data.daily.addAll(daily);
        }
    }

    private fun updateTypesToRefresh(data: WeatherData, typesToRefresh: Collection<WeatherDataType>): Collection<WeatherDataType> {
        val types = typesToRefresh.toArrayList();
        types.removeAll(data.types);
        return types;
    }

    private fun refreshWeatherDataUsingBackend(city: CityEntity, dbCity: CityEntity?, data: WeatherData, typesToRefresh: Collection<WeatherDataType>) {
        val response = if (city.serverId != null) {
            hwWeatherClient.weather().get(city.serverId, typesToRefresh);
        } else {
            val externalId = cityConverter.convert(city.serverExternalId as ExternalCityIdEntity);
            hwWeatherClient.weather().get(externalId, typesToRefresh);
        };
        // Refresh the city information using server data
        data.city = cityConverter.convert(response.city);
        if (city.id != null) {
            data.city!!.id = city.id;
        } else {
            data.city = cityManager.save(city);
        }
        // Then, refresh the database with data obtained from the server
        saveWeatherDataResponseInDatabase(data.city as CityEntity, response);
        // And will the weather data from the database.
        refreshWeatherDataFromCache(data.city as CityEntity, data, typesToRefresh);
    }

    private fun saveWeatherDataResponseInDatabase(city: CityEntity, response: WeatherResponse) {
        getResponseTypes(response).forEach { type ->
            when (type) {
                WeatherDataType.CURRENT -> {
                    //FIXME
                }
                WeatherDataType.HOURLY -> {
                    //FIXME
                }
                WeatherDataType.DAILY -> {
                    val dailyForecasts = response.daily
                            .map { weatherConverter.convert(it); }
                            .sortedBy { it.day };
                    // Assign the city to each new entity
                    dailyForecasts.forEach { it.city = city; }
                    // Clear data already cached in the database for the received days
                    val startDate = dailyForecasts.first().day as DateTime;
                    val endDate = dailyForecasts.last().day?.plusDays(1) as DateTime;
                    dailyForecastRepository.deleteByCityAndDateBetween(city, startDate, endDate);
                    // Then save the new data
                    dailyForecasts.forEach { dailyForecastRepository.create(it); };
                }
            }
        }
    }

    private fun getResponseTypes(response: WeatherResponse): Collection<WeatherDataType> {
        val types: MutableList<WeatherDataType> = arrayListOf();
        if (response.current != null) {
            types.add(WeatherDataType.CURRENT);
        }
        if (response.hourly != null && response.hourly.isNotEmpty()) {
            types.add(WeatherDataType.HOURLY);
        }
        if (response.daily != null && response.daily.isNotEmpty()) {
            types.add(WeatherDataType.DAILY);
        }
        return types;
    }
}
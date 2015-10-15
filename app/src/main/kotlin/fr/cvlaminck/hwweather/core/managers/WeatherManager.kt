package fr.cvlaminck.hwweather.core.managers

import android.content.Context
import fr.cvlaminck.hwweather.client.HwWeatherClient
import fr.cvlaminck.hwweather.client.reponses.weather.WeatherResponse
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.core.converters.CityConverter
import fr.cvlaminck.hwweather.core.converters.WeatherConverter
import fr.cvlaminck.hwweather.core.loaders.weather.GetWeatherLoader
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.dao.weather.CurrentWeatherRepository
import fr.cvlaminck.hwweather.data.dao.weather.DailyForecastRepository
import fr.cvlaminck.hwweather.data.dao.weather.HourlyForecastRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.ExternalCityIdEntity
import org.joda.time.DateTime
import javax.inject.Inject

public class WeatherManager @Inject constructor(
        private val currentWeatherRepository: CurrentWeatherRepository,
        private val dailyForecastRepository: DailyForecastRepository,
        private val hourlyForecastRepository: HourlyForecastRepository,
        private val cityManager: CityManager,
        private val cityConverter: CityConverter,
        private val weatherConverter: WeatherConverter,
        private val hwWeatherClient: HwWeatherClient
) {
    val numberOfDaily = 6; //TODO: Read the value from a configuration and maybe controlled by server.
    val numberOfHourly = 24;

    fun createLoaderForGetWeatherForCity(context: Context, city: CityEntity, typesToRefresh: Collection<WeatherDataType>) =
            GetWeatherLoader(context, city, typesToRefresh, this);

    fun getWeatherForCity(city: CityEntity, typesToRefresh: Collection<WeatherDataType>): WeatherData {
        var city: CityEntity = city;
        var data: WeatherData = WeatherData();
        // First, we check if the city is in our database.
        if (city.serverExternalId != null) {
            var dbCity = cityManager.findCityByExternalId(city.serverExternalId as ExternalCityIdEntity);
            if (dbCity != null) {
                city = dbCity;
            }
        }
        // If not there is no need to try to refresh the data using the database.
        if (city.id != null) {
            refreshWeatherDataFromCache(city, data, typesToRefresh);
        }
        // Recompute types to refresh once data loaded from the database
        val typesToRefreshUsingBackend = updateTypesToRefresh(data, typesToRefresh);
        // Refresh the missing values using the backend
        if (typesToRefreshUsingBackend.isNotEmpty()) {
            refreshWeatherDataUsingBackend(city, data, typesToRefreshUsingBackend);
        }
        return data;
    }

    private fun refreshWeatherDataFromCache(city: CityEntity, data: WeatherData, typesToRefresh: Collection<WeatherDataType>) {
        data.city = city;
        typesToRefresh.forEach { type ->
            when (type) {
                WeatherDataType.CURRENT -> {
                    data.current = currentWeatherRepository.findByCity(city);
                    if (data.current != null && data.current!!.expired) {
                        data.current = null;
                    }
                }
                WeatherDataType.HOURLY -> {
                    val startHour = DateTime.now()
                            .withMinuteOfHour(0)
                            .withSecondOfMinute(0)
                            .withMillisOfSecond(0);
                    val endHour = startHour.plusHours(numberOfHourly);

                    val hourly = hourlyForecastRepository.findByCityAndDateBetween(city, startHour, endHour)
                            .filter { hourly -> !hourly.expired; };
                    if (hourly.size() == numberOfHourly) {
                        data.hourly.addAll(hourly);
                    }
                }
                WeatherDataType.DAILY -> {
                    val startDay = DateTime.now().withTimeAtStartOfDay();
                    val endDay = startDay.plusDays(numberOfDaily);

                    val daily = dailyForecastRepository.findByCityAndDateBetween(city, startDay, endDay)
                            .filter { daily -> !daily.expired };
                    if (daily.size() == numberOfDaily) {
                        data.daily.addAll(daily);
                    }
                }
            }
        }
    }

    private fun updateTypesToRefresh(data: WeatherData, typesToRefresh: Collection<WeatherDataType>): Collection<WeatherDataType> {
        val types = typesToRefresh.toArrayList();
        types.removeAll(data.types);
        return types;
    }

    private fun refreshWeatherDataUsingBackend(city: CityEntity, data: WeatherData, typesToRefresh: Collection<WeatherDataType>) {
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
                    val current = weatherConverter.convert(response.current);
                    // Assign the city to the new entity
                    current.city = city;
                    // Clear data already cached in the database
                    currentWeatherRepository.deleteByCity(city);
                    // Then save the new data
                    currentWeatherRepository.create(current);
                }
                WeatherDataType.HOURLY -> {
                    val hourlyForecasts = response.hourly
                            .map { weatherConverter.convert(it); }
                            .sortedBy { it.hour };
                    // Assign the city to each new entity
                    hourlyForecasts.forEach { it.city = city; }
                    // Clear data already cached in the database for the received hours
                    val startHour = hourlyForecasts.first().hour as DateTime;
                    val endHour = hourlyForecasts.last().hour?.plusHours(1) as DateTime;
                    hourlyForecastRepository.deleteByCityAndHourBetween(city, startHour, endHour);
                    // Then save the new data
                    hourlyForecasts.forEach { hourlyForecastRepository.create(it); };
                }
                WeatherDataType.DAILY -> {
                    val dailyForecasts = response.daily
                            .map { weatherConverter.convert(it); }
                            .sortedBy { it.day };
                    // Assign the city to each new entity
                    dailyForecasts.forEach { it.city = city; }
                    // Clear data already cached in the database for the received days
                    val startDay = dailyForecasts.first().day as DateTime;
                    val endDay = dailyForecasts.last().day?.plusDays(1) as DateTime;
                    dailyForecastRepository.deleteByCityAndDayBetween(city, startDay, endDay);
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
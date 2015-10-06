package fr.cvlaminck.hwweather.core.loaders

import android.content.Context
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.core.model.Page
import fr.cvlaminck.hwweather.data.model.city.CityEntity

public class SearchCityLoader(
        context: Context,
        val query: String,
        val cityManager: CityManager
) : HwWeatherOperationLoader<Page<CityEntity>> (context) {

    override fun doRequest(): Page<CityEntity> {
        return cityManager.search(query);
    }

}
package fr.cvlaminck.hwweather.front.activities

import android.app.Activity
import android.os.Bundle
import fr.cvlaminck.hwweather.data.dao.city.FavoriteCityRepository
import javax.inject.Inject

class RoutingStartActivity: Activity() {

    @Inject
    lateinit var favoriteCityRepository: FavoriteCityRepository;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityToStart = if (favoriteCityRepository.countOf() == 0L) {
            SearchCityActivity::class.java
        } else {
            SearchCityActivity::class.java //Replace by the activity with favorite cities.
        }
    }

}
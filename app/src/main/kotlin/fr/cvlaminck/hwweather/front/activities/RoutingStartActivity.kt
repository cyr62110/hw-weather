package fr.cvlaminck.hwweather.front.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.core.managers.FavoriteCityManager
import fr.cvlaminck.hwweather.data.dao.city.FavoriteCityRepository
import javax.inject.Inject

class RoutingStartActivity: Activity() {

    @Inject
    lateinit var favoriteCityManager: FavoriteCityManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as HwWeatherApplication).component().inject(this);

        val activityToStart = if (!favoriteCityManager.hasFavorite) {
            SearchCityActivity::class.java
        } else {
            FavoriteCitiesWeatherActivity::class.java
        }
        val intent = Intent(this, activityToStart);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

}
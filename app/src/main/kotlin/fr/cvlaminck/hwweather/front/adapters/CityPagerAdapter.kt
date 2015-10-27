package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.core.managers.FavoriteCityManager
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import javax.inject.Inject

public class CityPagerAdapter (
        val context: Context,
        fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    private val favoriteCities: List<CityEntity> = listOf();

    @Inject
    lateinit var favoriteCityManager: FavoriteCityManager;

    init {
        (context.applicationContext as HwWeatherApplication).component().inject(this);
    }

    override fun getCount(): Int {
        throw UnsupportedOperationException()
    }

    override fun getItem(position: Int): Fragment? {
        throw UnsupportedOperationException()
    }
}
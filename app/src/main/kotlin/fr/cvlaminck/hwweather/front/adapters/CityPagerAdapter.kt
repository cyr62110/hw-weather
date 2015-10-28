package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.core.managers.FavoriteCityManager
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.front.fragments.WeatherFragment
import javax.inject.Inject

public class CityPagerAdapter (
        val context: Context,
        fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    private val favoriteCities: MutableList<CityEntity> = arrayListOf();

    @Inject
    lateinit var favoriteCityManager: FavoriteCityManager;

    init {
        (context.applicationContext as HwWeatherApplication).component().inject(this);

        loadFavoriteCities();
    }

    private fun loadFavoriteCities() {
        favoriteCities.clear();
        favoriteCities.addAll(favoriteCityManager.orderedFavoriteCities);
    }

    override fun getCount(): Int = favoriteCities.size;

    override fun getPageTitle(position: Int): CharSequence = favoriteCities[position].name as String;

    override fun getItem(position: Int): Fragment {
        return WeatherFragment.newInstance(context, favoriteCities[position]);
    }

    override fun notifyDataSetChanged() {
        loadFavoriteCities()
        super.notifyDataSetChanged();
    }
}
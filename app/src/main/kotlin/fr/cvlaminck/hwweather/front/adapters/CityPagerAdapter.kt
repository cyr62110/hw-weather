package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.core.managers.FavoriteCityManager
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.front.fragments.WeatherFragment
import java.util.*
import javax.inject.Inject

public class CityPagerAdapter (
        val context: Context,
        val fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    @Inject
    lateinit var favoriteCityManager: FavoriteCityManager;

    private val favoriteCities: MutableList<CityEntity> = arrayListOf();
    val favoriteCityFragments: HashMap<CityEntity, WeatherFragment?> = hashMapOf();

    init {
        (context.applicationContext as HwWeatherApplication).component().inject(this);

        loadFavoriteCities();
    }

    private fun loadFavoriteCities() {
        val orderedFavoriteCities = favoriteCityManager
                .orderedFavoriteCities
                .distinct(); //FIXME: There is a bug allowing to have the same favorite twice

        val removedFavoriteCities = favoriteCities
                .filter { !orderedFavoriteCities.contains(it); }
        if (removedFavoriteCities.size > 0) {
            val ft = fragmentManager.beginTransaction();
            for (city in removedFavoriteCities) {
                val fragment = favoriteCityFragments[city];
                if (fragment != null) {
                    ft.remove(fragment as Fragment);
                }
                favoriteCityFragments.remove(city);
            }
            ft.commit();
        }

        favoriteCities.clear();
        favoriteCities.addAll(orderedFavoriteCities);
    }

    override fun getCount(): Int = favoriteCities.size;

    override fun getPageTitle(position: Int): CharSequence = favoriteCities[position].name as String;

    override fun getItem(position: Int): Fragment {
        val city = favoriteCities[position];
        var fragment = favoriteCityFragments[city];
        if (fragment == null) {
            fragment = WeatherFragment.newInstance(context, city);
            favoriteCityFragments[city] = fragment;
        }
        return fragment;
    }

    override fun notifyDataSetChanged() {
        loadFavoriteCities()
        super.notifyDataSetChanged();
    }
}
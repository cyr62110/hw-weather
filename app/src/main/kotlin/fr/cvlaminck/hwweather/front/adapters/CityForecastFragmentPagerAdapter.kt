package fr.cvlaminck.hwweather.front.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.cvlaminck.hwweather.data.model.City
import fr.cvlaminck.hwweather.front.fragments.CityForecastFragment

public class CityForecastFragmentPagerAdapter(
        fm: FragmentManager,
        val cities : List<City>
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = cities.size();

    override fun getPageTitle(position: Int): CharSequence? = cities.get(position).name;

    override fun getItem(position: Int): Fragment? {
        return CityForecastFragment.newInstance(cities.get(position));
    }
}
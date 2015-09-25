package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.front.fragments.CityForecastFragment

public class CityForecastFragmentPagerAdapter(
        private val context: Context,
        fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    var cities: List<CityEntity> = listOf();
        set(value) {
            $cities = value;
            notifyDataSetChanged();
        }

    override fun getCount(): Int = cities.size();

    override fun getPageTitle(position: Int): CharSequence? = cities.get(position).name;

    override fun getItem(position: Int): Fragment? {
        return CityForecastFragment.newInstance(cities.get(position));
    }
}
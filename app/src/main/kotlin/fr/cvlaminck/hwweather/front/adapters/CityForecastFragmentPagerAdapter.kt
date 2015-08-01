package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.database.Cursor
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import fr.cvlaminck.hwweather.data.HwWeatherProvider
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.data.tables.CityTableColumns
import fr.cvlaminck.hwweather.front.fragments.CityForecastFragment

public class CityForecastFragmentPagerAdapter(
        private val context: Context,
        fm: FragmentManager
) : FragmentPagerAdapter(fm), Loader.OnLoadCompleteListener<Cursor> {

    private val loader: CursorLoader = createCursorLoader();
    private var cities: List<CityEntity> = listOf();

    private fun createCursorLoader(): CursorLoader {
        val loader = CursorLoader(context);
        loader.setUri(HwWeatherProvider.Cities.cities);
        loader.registerListener(42, this);
        loader.setProjection(CityTableColumns.projection);
        loader.startLoading();

        return loader;
    }

    fun cancel() {
        loader.cancelLoad();
    }

    override fun getCount(): Int = cities.size();

    override fun getPageTitle(position: Int): CharSequence? = cities.get(position).name;

    override fun getItem(position: Int): Fragment? {
        return CityForecastFragment.newInstance(cities.get(position));
    }

    override fun onLoadComplete(loader: Loader<Cursor>?, data: Cursor?) {
        var cities = listOf<CityEntity>();
        if (data != null) {
            while (data!!.moveToNext()) {
                cities += CityEntity.fromCursor(data);
            }
        }
        this.cities = cities;
        notifyDataSetChanged();
    }
}
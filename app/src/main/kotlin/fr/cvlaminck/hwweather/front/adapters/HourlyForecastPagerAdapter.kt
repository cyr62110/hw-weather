package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import fr.cvlaminck.hwweather.front.views.HourlyForecastView

public class HourlyForecastPagerAdapter(
        private val context: Context,
        val hourlyForecasts: List<HourlyForecastEntity>) : PagerAdapter() {

    override fun getCount(): Int = hourlyForecasts.size();

    override fun isViewFromObject(view: View?, obj: Any?): Boolean = view == obj;

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val view = HourlyForecastView(context);
        view.hourlyForecast = hourlyForecasts.get(position);

        container!!.addView(view);
        return view;
    }

    override fun destroyItem(container: ViewGroup?, position: Int, view: Any?) {
        container!!.removeView(view as View);
    }
}
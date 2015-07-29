package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.HourlyForecast
import fr.cvlaminck.hwweather.front.views.HourlyForecastView

public class HourlyForecastPagerAdapter(
        private val context: Context,
        val hourlyForecasts: List<HourlyForecast>) : PagerAdapter() {

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
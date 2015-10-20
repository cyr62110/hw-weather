package fr.cvlaminck.hwweather.front.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import fr.cvlaminck.hwweather.front.views.HourlyForecastView

public class HourlyForecastPagerAdapter(
        private val context: Context) : PagerAdapter() {

    var currentWeather: CurrentWeatherEntity? = null
        set(currentWeather: CurrentWeatherEntity?) {
            if (currentWeather != field) {
                field = currentWeather;
                notifyDataSetChanged();
            }
        };

    var hourlyForecasts: List<HourlyForecastEntity>? = null
        set(hourlyForecasts: List<HourlyForecastEntity>?) {
            if (field != hourlyForecasts) {
                field = hourlyForecasts;
                notifyDataSetChanged();
            }
        }
        get() : List<HourlyForecastEntity>? {
            return field?.filter { it.hour!!.isAfterNow };
        }

    override fun getCount(): Int {
        var count = 0;
        if (currentWeather != null) {
            count += 1;
        }
        if (hourlyForecasts != null) {
            count += hourlyForecasts!!.size();
        }
        return count;
    };

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj;

    override fun instantiateItem(container: ViewGroup, position: Int): Any? {
        val view = HourlyForecastView(context);
        if (position == 0) {
            view.currentWeather = currentWeather;
        } else {
            view.hourlyForecast = hourlyForecasts?.get(position - 1);
        }
        container.addView(view);
        return view;
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View);
    }
}
package fr.cvlaminck.hwweather.front.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import fr.cvlaminck.hwweather.front.adapters.HourlyForecastPagerAdapter
import fr.cvlaminck.hwweather.views.CircleSliderLayout
import kotlinx.android.synthetic.hourlyforecastfragment.cslHour
import kotlinx.android.synthetic.hourlyforecastfragment.vpHourlyForecast

public class HourlyForecastFragment : Fragment() {
    private var adapter : HourlyForecastPagerAdapter? = null;

    var currentWeather: CurrentWeatherEntity? = null
        set(currentWeather: CurrentWeatherEntity?) {
            field = currentWeather;
            updateViews();
        }

    var hourlyForecasts: List<HourlyForecastEntity>? = listOf()
        set(hourlyForecasts: List<HourlyForecastEntity>?) {
            field = if (hourlyForecasts == null) {
                listOf();
            } else {
                hourlyForecasts
                        .filter { it.hour!!.isAfterNow }
                        .sortedBy { it.hour };
            }
            updateViews();
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hourlyforecastfragment, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = HourlyForecastPagerAdapter(activity);
        vpHourlyForecast.adapter = adapter;
        cslHour.onCircleSliderLayoutChangeListener = onCircleSliderLayoutChangeListener;
    }

    private fun updateViews() {
        if (adapter != null) {
            //Update the values in the adapter
            adapter!!.currentWeather = currentWeather;
            adapter!!.hourlyForecasts = hourlyForecasts;

            //Update the start offset of the circle TODO

        }
    }



    private val onCircleSliderLayoutChangeListener = object : CircleSliderLayout.OnCircleSliderLayoutChangeListener {
        override fun onProgressChanged(circleSliderLayout: CircleSliderLayout, progress: Double, byUser: Boolean) {

        }

        override fun onStartTrackingTouch(circleSliderLayout: CircleSliderLayout) {}
        override fun onStopTrackingTouch(circleSliderLayout: CircleSliderLayout) {}
    }
}
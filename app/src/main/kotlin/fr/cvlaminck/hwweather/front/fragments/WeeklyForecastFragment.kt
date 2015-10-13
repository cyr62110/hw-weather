package fr.cvlaminck.hwweather.front.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.front.views.DailyForecastView
import kotlinx.android.synthetic.weeklyforecastfragment.*;

public class WeeklyForecastFragment : Fragment() {

    private var _dailyForecasts = arrayListOf<DailyForecastEntity>();
    var dailyForecasts: Collection<DailyForecastEntity>?
        get() = _dailyForecasts;
        set(forecasts: Collection<DailyForecastEntity>?) {
            if (forecasts == null) {
                _dailyForecasts.clear();
            } else {
                _dailyForecasts = forecasts
                        .sortedBy { forecast -> forecast.day }
                        .toArrayList();
            }
            updateDailyForecasts();
        };

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weeklyforecastfragment, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun updateDailyForecasts() {
        if (_dailyForecasts.isEmpty()) {
            llWeekly.removeAllViews();
        }
        for (i in 0..6) {
            val dailyForecastView = llWeekly.getChildAt(i);

        }
    }

    private fun createDailyForecastView(dailyForecastEntity: DailyForecastEntity): DailyForecastView {
        val layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        val view = DailyForecastView(activity);
        view.layoutParams = layoutParams;

        return view;
    }

}
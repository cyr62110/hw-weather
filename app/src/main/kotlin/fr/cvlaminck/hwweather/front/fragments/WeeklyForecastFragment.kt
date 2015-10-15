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
        updateDailyForecasts();
    }

    private fun updateDailyForecasts() {
        if (llWeekly == null) { // If the UI has not been initialized at this point
            return;
        }
        if (_dailyForecasts.isEmpty()) {
            llWeekly.removeAllViews();
            return;
        }
        while (llWeekly.childCount > _dailyForecasts.size()) {
            llWeekly.removeViewsInLayout(0, 1);
        }
        for (i in 0.._dailyForecasts.size()-1) {
            val dailyForecastView: DailyForecastView = if (i >= llWeekly.childCount) {
                val view = createDailyForecastView();
                llWeekly.addView(view);
                view;
            } else {
                llWeekly.getChildAt(i) as DailyForecastView;
            }
            dailyForecastView.daily = _dailyForecasts.get(i);
        }
        llWeekly.requestLayout();
        llWeekly.invalidate();
    }

    private fun createDailyForecastView(): DailyForecastView {
        val layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        val view = DailyForecastView(activity);
        view.layoutParams = layoutParams;

        return view;
    }

}
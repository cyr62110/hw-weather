package fr.cvlaminck.hwweather.front.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.front.views.DailyForecastView

public class WeeklyForecastFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.weeklyforecastfragment, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        for (i in 1..7) {
            appendDailyForecastView(view as ViewGroup);
        }
    }

    fun appendDailyForecastView(viewGroup: ViewGroup) {
        val layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        val view = DailyForecastView(getActivity());
        //FIXME : set daily forecast data object
        viewGroup.addView(view, layoutParams);
    }

}
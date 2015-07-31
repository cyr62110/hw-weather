package fr.cvlaminck.hwweather.front.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity
import fr.cvlaminck.hwweather.front.adapters.HourlyForecastPagerAdapter
import fr.cvlaminck.hwweather.views.CircleSliderLayout
import kotlinx.android.synthetic.hourlyforecastfragment.*;

public class HourlyForecastFragment : Fragment() {

    var onHourChangeListener: OnHourChangeListener? = null;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.hourlyforecastfragment, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = HourlyForecastPagerAdapter(getActivity(), listOf(HourlyForecastEntity(), HourlyForecastEntity()));
        vpHourlyForecast.setAdapter(adapter);
        cslHour.setOnCircleSliderLayoutChangeListener(OnCircleSliderLayoutChangeListener());
    }

    private class OnCircleSliderLayoutChangeListener : CircleSliderLayout.OnCircleSliderLayoutChangeListener {
        override fun onProgressChanged(circleSliderLayout: CircleSliderLayout, progress: Double, byUser: Boolean) {

        }

        override fun onStartTrackingTouch(circleSliderLayout: CircleSliderLayout?) {

        }

        override fun onStopTrackingTouch(circleSliderLayout: CircleSliderLayout?) {

        }
    }

    interface OnHourChangeListener {
        fun onHourChanged(hour: Double);
    }
}
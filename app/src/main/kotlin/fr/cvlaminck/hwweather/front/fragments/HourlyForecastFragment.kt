package fr.cvlaminck.hwweather.front.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType
import org.joda.time.Period

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

    private val pageSizeInProgress: Float
        get() = 1.0f / adapter!!.count;

    private val firstHour: DateTime
        get(): DateTime {
            var firstHour = DateTime.now().withSecondOfMinute(0);
            if (currentWeather != null) {
                firstHour = currentWeather!!.hour;
            } else if (hourlyForecasts != null && hourlyForecasts!!.isNotEmpty()) {
                firstHour = hourlyForecasts!!.first().hour as DateTime;
            }
            return firstHour;
        };

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

            //Update the progress value to match the first hour
            val progress = getProgressForHour(firstHour);
            Log.d(this.javaClass.simpleName, "progress: " + progress.toString());
            cslHour.setProgressValue(progress);

            //Update the start offset of the circle
            var startAngle: Float = 0f;
            if (currentWeather != null) {
                startAngle = getStartAngleForHour(DateTime.now());
            } else if (hourlyForecasts != null && hourlyForecasts!!.isNotEmpty()) {
                startAngle = getStartAngleForHour(hourlyForecasts!!.first().hour as DateTime);
            }
            Log.d(this.javaClass.simpleName, "startAngle: " + startAngle.toString());
            //cslHour.setProgressStartOffset(startAngle);
        }
    }

    private fun getStartAngleForHour(hour: DateTime) =
            hour.get(DateTimeFieldType.hourOfDay()) / 24.0f * 360.0f;

    private fun getPageNumberForProgress(progress: Float) =
            (progress / pageSizeInProgress).toInt();

    private fun getProgressForHour(hour: DateTime): Float {
        val period = Period(firstHour.withMinuteOfHour(0), hour.withSecondOfMinute(0));
        return (period.minutes / 60.0f) * pageSizeInProgress;
    }

    private val onCircleSliderLayoutChangeListener = object : CircleSliderLayout.OnCircleSliderLayoutChangeListener {
        override fun onProgressChanged(circleSliderLayout: CircleSliderLayout, progress: Float, byUser: Boolean) {
            if (byUser) { // We only want
                val pageNumber = getPageNumberForProgress(progress);
                if (pageNumber != vpHourlyForecast.currentItem) {
                    vpHourlyForecast.setCurrentItem(pageNumber, true);
                }
            }
        }

        override fun onStopTrackingTouch(circleSliderLayout: CircleSliderLayout) {

        }

        override fun onStartTrackingTouch(circleSliderLayout: CircleSliderLayout) {}
    }
}
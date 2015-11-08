package fr.cvlaminck.hwweather.front.fragments

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import fr.cvlaminck.hwweather.front.adapters.HourlyForecastPagerAdapter
import fr.cvlaminck.hwweather.utils.isAfterNowUTC
import fr.cvlaminck.hwweather.utils.nowUTC
import fr.cvlaminck.hwweather.views.CircleSliderLayout
import kotlinx.android.synthetic.hourlyforecastfragment.cslHour
import kotlinx.android.synthetic.hourlyforecastfragment.vpHourlyForecast
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType
import org.joda.time.LocalDateTime
import org.joda.time.Period
import java.text.FieldPosition

public class HourlyForecastFragment : Fragment() {
    private var adapter : HourlyForecastPagerAdapter? = null;

    private var progressAnimator: ObjectAnimator? = null;
    private var progressAnimated: Boolean = false;

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
                        .filter { it.hour!!.isAfterNowUTC() }
                        .sortedBy { it.hour };
            }
            updateViews();
            updateCircleSliderInnerContentOffset();
        }

    var circleSliderInnerContentOffset: Float = 0f
        set(offset: Float) {
            field = if (offset > 1) 1f else if (offset < -1f) -1f else offset;
            updateCircleSliderInnerContentOffset();
        }

    private val pageSizeInProgress: Float
        get() = 1.0f / adapter!!.count;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hourlyforecastfragment, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = HourlyForecastPagerAdapter(activity);
        vpHourlyForecast.adapter = adapter;
        cslHour.onCircleSliderLayoutChangeListener = onCircleSliderLayoutChangeListener;

        enableViewPagerScrollTracking();
    }

    private fun updateViews() {
        if (adapter != null) {
            //Update the values in the adapter
            adapter!!.currentWeather = currentWeather;
            adapter!!.hourlyForecasts = hourlyForecasts;

            //Update the progress value to match the first hour
            val progress = getProgressForHour(getHourForPosition(0));
            cslHour.progressValue = progress;

            //Update the start offset of the circle
            var startAngle: Float = 0f;
            if (currentWeather != null) {
                startAngle = getStartAngleForHour(DateTime.now());
            } else if (hourlyForecasts != null && hourlyForecasts!!.isNotEmpty()) {
                startAngle = getStartAngleForHour(hourlyForecasts!!.first().hour as DateTime);
            }
            cslHour.progressStartOffset = startAngle;
        }
    }

    private fun updateCircleSliderInnerContentOffset() {
        if (adapter != null) {
            vpHourlyForecast.translationX = circleSliderInnerContentOffset * vpHourlyForecast.width;
        }
    }

    private fun enableViewPagerScrollTracking() {
        vpHourlyForecast.addOnPageChangeListener(vpHourlyForecastOnPageChangeListener);
    }

    private fun disableViewPagerScrollTracking() {
        vpHourlyForecast.removeOnPageChangeListener(vpHourlyForecastOnPageChangeListener);
    }

    private fun getStartAngleForHour(hour: DateTime) =
            hour.get(DateTimeFieldType.hourOfDay()) / 24.0f * 360.0f;

    private fun getHourForPosition(position: Int): LocalDateTime {
        if (position == 0) {
            var hour = nowUTC().withSecondOfMinute(0);
            if (currentWeather != null) {
                hour = currentWeather!!.hour;
            } else if (hourlyForecasts != null && hourlyForecasts!!.isNotEmpty()) {
                hour = hourlyForecasts!!.first().hour as LocalDateTime;
            }
            return hour;
        } else {
            var hour = nowUTC()
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .plusHours(position);
            val hourlyIndex = if (currentWeather != null) position - 1 else position;
            if (hourlyForecasts != null && hourlyForecasts!!.size > hourlyIndex) {
                hour = hourlyForecasts!![hourlyIndex].hour as LocalDateTime;
            }
            return hour;
        }
    }

    private fun getPageNumberForProgress(progress: Float): Int {
        val pageNumber = (progress / pageSizeInProgress).toInt();
        return if (pageNumber < adapter!!.count) pageNumber else adapter!!.count - 1;
    }

    private fun getProgressForHour(hour: LocalDateTime): Float {
        val minutes = Period(getHourForPosition(0).withMinuteOfHour(0), hour.withSecondOfMinute(0)).toStandardMinutes();
        return (minutes.minutes / 60.0f) * pageSizeInProgress;
    }

    private fun animateProgressValueToMathHour() {
        // When the user has finished to drag the progess,
        // we animate so the position of the thumb correspond to an hour.

        val pageNumber = getPageNumberForProgress(cslHour.progressValue);
        val hour = if (currentWeather != null && pageNumber == 0) {
            currentWeather!!.hour;
        } else if (currentWeather != null && pageNumber > 0) {
            hourlyForecasts!![pageNumber - 1].hour;
        } else {
            hourlyForecasts!![pageNumber].hour;
        };

        val targetProgressValue = getProgressForHour(hour as LocalDateTime);

        if (targetProgressValue != cslHour.progressValue) {
            progressAnimator = ObjectAnimator.ofFloat(cslHour, CircleSliderLayout.PROGRESS, targetProgressValue)
                    .setDuration(300);
            progressAnimator!!.addListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    progressAnimated = true;
                }
                override fun onAnimationEnd(animation: Animator?) {
                    progressAnimated = false;
                    enableViewPagerScrollTracking();
                }
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
            });
            progressAnimator!!.start();
        } else {
            enableViewPagerScrollTracking();
        }
    }

    private val onCircleSliderLayoutChangeListener = object : CircleSliderLayout.OnCircleSliderLayoutChangeListener {
        override fun onProgressChanged(circleSliderLayout: CircleSliderLayout, progress: Float, byUser: Boolean) {
            if (byUser) { // Animation do not change the page, so we optimize a bit.
                val pageNumber = getPageNumberForProgress(progress);
                if (pageNumber != vpHourlyForecast.currentItem) {
                    vpHourlyForecast.setCurrentItem(pageNumber, true);
                }
            }
        }

        override fun onStartTrackingTouch(circleSliderLayout: CircleSliderLayout) {
            // When the user is moving the progress, we don't want the view pager listener to interact.
            disableViewPagerScrollTracking();
        }

        override fun onStopTrackingTouch(circleSliderLayout: CircleSliderLayout) {
            animateProgressValueToMathHour();
        }
    }

    private val vpHourlyForecastOnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {}
        override fun onPageScrollStateChanged(state: Int) {

        }
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // We synchronize the progress with the page scroll.
            if (adapter == null || position >= adapter!!.count) {
                return;
            }
            val hour = getHourForPosition(position);
            val siblingHour = getHourForPosition(position + 1);

            val period = Period(hour, siblingHour);
            val minutesOffset: Int = (positionOffset * period.toStandardMinutes().minutes).toInt();

            cslHour.progressValue = getProgressForHour(hour.plusMinutes(minutesOffset));
        }
    }
}
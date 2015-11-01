package fr.cvlaminck.hwweather.front.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.util.Log
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.front.adapters.CityPagerAdapter
import fr.cvlaminck.hwweather.front.fragments.WeatherFragment
import kotlinx.android.synthetic.favoritecitiesweatheractivity.*;

public class FavoriteCitiesWeatherActivity : FragmentActivity() {

    private var adapter: CityPagerAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritecitiesweatheractivity);

        adapter = CityPagerAdapter(this, this.supportFragmentManager);
        vpCities.adapter = adapter;
    }

    override fun onStart() {
        super.onStart()

        val weatherFragment = adapter!!.getItem(0) as WeatherFragment;
        weatherFragment.hourlyForecastContentOffset = 0.5f;

        vpCities.addOnPageChangeListener(vpCitiesOnPageChangeListener);
    }

    override fun onStop() {
        super.onStop()

        vpCities.clearOnPageChangeListeners();
    }

    private val vpCitiesOnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {

        }

        override fun onPageScrollStateChanged(state: Int) {
            if (adapter == null) {
                return;
            }
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                // When the scroll is finished, we reset all the content offset.
                for (position in 0..adapter!!.count - 1) {
                    val weatherFragment = adapter!!.getItem(position) as WeatherFragment;
                    weatherFragment.hourlyForecastContentOffset = 0f;
                }
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (position == adapter!!.count - 1) {
                return;
            }

            // We create a parallax effect when the user scrolls between page.
            val weatherFragment = adapter!!.getItem(position) as WeatherFragment;
            weatherFragment.hourlyForecastContentOffset = positionOffset;

            val siblingWeatherFragment = adapter!!.getItem(position + 1) as WeatherFragment;
            siblingWeatherFragment.hourlyForecastContentOffset = -1f + positionOffset;
        }
    }

}
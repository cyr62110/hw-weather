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
            Log.d("vpCities", "Selected : " + position.toString())
        }

        override fun onPageScrollStateChanged(state: Int) {
            Log.d("vpCities", "State : " + state.toString())
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                //FIXME reset all page offset.
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            Log.d("vpCities", "Scroll : " + position.toString() + " " + positionOffset.toString())

            val weatherFragment = adapter!!.getItem(position) as WeatherFragment;
            //weatherFragment.hourlyForecastContentOffset = positionOffset;
        }
    }

}
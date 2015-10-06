package fr.cvlaminck.hwweather.front.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.front.fragments.WeatherFragment
import kotlinx.android.synthetic.weatheractivity.*

public class WeatherActivity : FragmentActivity() {
    companion object {
        val INTENT_CITY = "city";

        val WEATHER_FRAGMENT_ID = 42;
    }

    private var _city: CityEntity? = null;
    val city: CityEntity
        get() {
            if (_city == null) {
                _city = intent.getParcelableExtra(INTENT_CITY);
            }
            return _city as CityEntity;
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        _city = null;
        setContentView(R.layout.weatheractivity);

        initWeatherFragment();
    }

    private fun initWeatherFragment() {
        val ft = supportFragmentManager.beginTransaction();
        val weatherFragment = WeatherFragment.newInstance(this, city);
        ft.add(R.id.flWeather, weatherFragment);
        ft.commit()
    }


}
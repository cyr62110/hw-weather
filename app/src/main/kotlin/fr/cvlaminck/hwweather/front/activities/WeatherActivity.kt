package fr.cvlaminck.hwweather.front.activities

import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.ThemeManager
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.front.fragments.WeatherFragment
import kotlinx.android.synthetic.weatheractivity.*
import javax.inject.Inject

public class WeatherActivity : FragmentActivity() {
    companion object {
        val INTENT_CITY = "city";

        val WEATHER_FRAGMENT_ID = 42;
    }

    @Inject
    lateinit var themeManager: ThemeManager;

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
        (application as HwWeatherApplication).component().inject(this);
        setContentView(R.layout.weatheractivity);

        applyTheme();

        initWeatherFragment();
    }

    private fun applyTheme() {
        val theme = themeManager.theme;
        val background = theme.getBackground(city);
        val applicationTint = theme.getApplicationTint(city);

        background.apply(flWeather);
        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor = applicationTint;
            window.statusBarColor = applicationTint;
        }
    }

    private fun initWeatherFragment() {
        val ft = supportFragmentManager.beginTransaction();
        val weatherFragment = WeatherFragment.newInstance(this, city);
        ft.add(R.id.flWeather, weatherFragment);
        ft.commit()
    }


}
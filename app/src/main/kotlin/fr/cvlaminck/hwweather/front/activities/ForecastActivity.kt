package fr.cvlaminck.hwweather.front.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import butterknife.ButterKnife
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.front.adapters.CityForecastFragmentPagerAdapter
import kotlinx.android.synthetic.forecastactivity.vpForecast
import javax.inject.Inject

public class ForecastActivity : FragmentActivity() {

    lateinit val cityManager: CityManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecastactivity);
        (getApplication() as HwWeatherApplication).component()?.inject(this);
        ButterKnife.bind(this);

        initViewPager();
    }

    private fun initViewPager() {
        val cities = cityManager!!.getCities();
        val adapter = CityForecastFragmentPagerAdapter(this, getSupportFragmentManager());
        adapter.cities = cities;
        vpForecast.setAdapter(adapter);
    }
}
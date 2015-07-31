package fr.cvlaminck.hwweather.front.activities

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.data.model.CityEntity
import fr.cvlaminck.hwweather.front.adapters.CityForecastFragmentPagerAdapter
import javax.inject.Inject
import kotlinx.android.synthetic.forecastactivity.*;

public class ForecastActivity : FragmentActivity() {

    @Inject @publicField
    private var cityManager: CityManager? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecastactivity);
        (getApplication() as HwWeatherApplication).component()?.inject(this);
        ButterKnife.bind(this);

        initViewPager();
    }

    private fun initViewPager() {
        val cities = cityManager!!.getCities();
        val adapter = CityForecastFragmentPagerAdapter(this.getSupportFragmentManager(), cities);
        vpForecast.setAdapter(adapter);
    }
}
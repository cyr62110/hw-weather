package fr.cvlaminck.hwweather.front.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import butterknife.ButterKnife
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.CityManager
import kotlinx.android.synthetic.forecastactivity.vpForecast
import javax.inject.Inject

public class WeatherActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatheractivity);
    }

}
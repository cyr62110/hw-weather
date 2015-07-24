package fr.cvlaminck.hwweather.front.activities

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import fr.cvlaminck.hwweather.HwWeatherApplication

import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.CityManager
import java.util.*
import javax.inject.Inject

public class ForecastActivity : Activity() {

    @Inject @publicField
    private var cityManager: CityManager? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        (getApplication() as HwWeatherApplication).component()?.inject(this);

        setContentView(R.layout.forecastactivity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddCity)
    fun btnAddCityClicked(btn: View) {
        Toast.makeText(this, Objects.toString(lI), Toast.LENGTH_SHORT).show();
    }
}
package fr.cvlaminck.hwweather.front.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.IconSetManager
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.WeatherCondition
import javax.inject.Inject

class DailyForecastView(context: Context) : LinearLayout(context) {
    init {
        (context.getApplicationContext() as HwWeatherApplication).component().inject(this);

        setContentView();
        bindViews();
        updateViews();
    }

    @Inject
    lateinit var iconSetManager: IconSetManager;

    var forecast: DailyForecastEntity? = null;

    var imgCondition: ImageView? = null;
    var txtMinTemperature: TextView? = null;
    var txtMaxTemperature: TextView? = null;
    var txtDay: TextView? = null;

    private fun setContentView() {
        val layoutInflater = LayoutInflater.from(getContext());
        val inflatedLayout: ViewGroup = layoutInflater.inflate(R.layout.dailyforecastview, null) as ViewGroup;
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(inflatedLayout, layoutParams);
    }

    private fun bindViews() {
        imgCondition = findViewById(R.id.imgCondition) as ImageView;
        txtMinTemperature = findViewById(R.id.txtMinTemperature) as TextView;
        txtMaxTemperature = findViewById(R.id.txtMaxTemperature) as TextView;
        txtDay = findViewById(R.id.txtDay) as TextView;
    }

    fun updateViews() {
        imgCondition!!.setImageDrawable(iconSetManager!!.getThumbnailForWeatherCondition(WeatherCondition.CLEAR));
        txtMinTemperature!!.setText("12");
        txtMaxTemperature!!.setText("13");
        txtDay!!.setText("Tue");
    }

}
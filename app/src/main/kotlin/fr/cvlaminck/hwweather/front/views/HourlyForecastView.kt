package fr.cvlaminck.hwweather.front.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.IconSetManager
import fr.cvlaminck.hwweather.data.model.HourlyForecastEntity
import javax.inject.Inject

class HourlyForecastView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    init {
        (context.applicationContext as HwWeatherApplication).component().inject(this);

        setContentView();
        bindViews();
        updateViews();
    }

    constructor(context: Context) : this(context, null) {
    }

    @Inject
    lateinit val iconSetManager: IconSetManager;

    var hourlyForecast: HourlyForecastEntity? = null;

    private var imgCondition: ImageView? = null;
    private var txtTemperature: TextView? = null;

    private fun setContentView() {
        val layoutInflater = LayoutInflater.from(context);
        val contextView: ViewGroup = layoutInflater.inflate(R.layout.hourlyforecastview, null) as ViewGroup;
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(contextView, layoutParams);
    }

    private fun bindViews() {
        imgCondition = findViewById(R.id.imgCondition) as ImageView;
        txtTemperature = findViewById(R.id.txtTemperature) as TextView;
    }

    private fun updateViews() {
        if (hourlyForecast != null) {
            val weatherConditionDrawable = iconSetManager.getIconForWeatherCondition(hourlyForecast!!.condition);

            imgCondition!!.setImageDrawable(weatherConditionDrawable);
            txtTemperature!!.text = "13";
        }
    }

}
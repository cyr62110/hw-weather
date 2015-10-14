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
import fr.cvlaminck.hwweather.core.managers.UserPreferencesManager
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.front.formatters.DateFormatter
import fr.cvlaminck.hwweather.front.formatters.TemperatureFormatter
import org.joda.time.DateTime
import javax.inject.Inject

public class DailyForecastView(context: Context) : LinearLayout(context) {
    init {
        (context.applicationContext as HwWeatherApplication).component().inject(this);

        inflateContentView();
        bindViews();
        updateViewsContent();
    }

    var dailyForecast: DailyForecastEntity? = null
        get() = field;
        set(forecast: DailyForecastEntity?) {
            field = forecast;
            updateViewsContent();
        };

    @Inject
    lateinit var temperatureFormatter: TemperatureFormatter;

    @Inject
    lateinit var dateFormatter: DateFormatter;

    @Inject
    lateinit var iconSetManager: IconSetManager;

    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager;

    var daily: DailyForecastEntity? = null;

    var imgCondition: ImageView? = null;
    var txtMinTemperature: TextView? = null;
    var txtMaxTemperature: TextView? = null;
    var txtDay: TextView? = null;

    private fun inflateContentView() {
        val layoutInflater = LayoutInflater.from(context);
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

    private fun emptyViewsContent() {
        imgCondition!!.setImageDrawable(null);
        txtMinTemperature!!.text = null;
        txtMaxTemperature!!.text = null;
        txtDay!!.text = null;
    }

    private fun updateViewsContent() {
        val temperatureUnit = userPreferencesManager.temperatureUnit;

        if (daily == null) {
            emptyViewsContent();
        } else {
            imgCondition!!.setImageDrawable(iconSetManager.getThumbnailForWeatherCondition(daily!!.condition));
            txtMinTemperature!!.text = temperatureFormatter.formatDaily(daily!!.minTemperatureInCelsius, temperatureUnit);
            txtMaxTemperature!!.text = temperatureFormatter.formatDaily(daily!!.maxTemperatureInCelsius, temperatureUnit);
            txtDay!!.text = dateFormatter.formatDayForDaily(daily!!.day as DateTime);
        }
    }

}
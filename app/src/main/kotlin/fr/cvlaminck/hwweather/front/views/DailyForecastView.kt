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
import fr.cvlaminck.hwweather.core.managers.UserPreferencesManager
import fr.cvlaminck.hwweather.core.model.weather.TemperatureUnit
import fr.cvlaminck.hwweather.data.model.weather.DailyForecastEntity
import fr.cvlaminck.hwweather.front.formatters.DateFormatter
import fr.cvlaminck.hwweather.front.formatters.TemperatureFormatter
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import javax.inject.Inject

public class DailyForecastView : LinearLayout {
    @Inject
    lateinit var temperatureFormatter: TemperatureFormatter;

    @Inject
    lateinit var dateFormatter: DateFormatter;

    @Inject
    lateinit var iconSetManager: IconSetManager;

    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager;

    var imgCondition: ImageView? = null;
    var txtMinTemperature: TextView? = null;
    var txtMaxTemperature: TextView? = null;
    var txtDay: TextView? = null;

    var daily: DailyForecastEntity? = null;
        set(daily: DailyForecastEntity?) {
            field = daily;
            updateViewsContent();
        }

    public constructor(context: Context) : super(context) {
        init();
    }

    public constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init();
    }

    public constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init();
    }

    private fun init() {
        if (!isInEditMode) {
            (context.applicationContext as HwWeatherApplication).component().inject(this);
        }

        inflateContentView();
        bindViews();
        updateViewsContent();
    }

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
        if (isInEditMode) {
            return; // In the IDE, we want to keep the fake values
        }
        val temperatureUnit = userPreferencesManager.temperatureUnit;

        if (daily == null) {
            emptyViewsContent();
        } else {
            imgCondition!!.setImageDrawable(iconSetManager.getThumbnailForWeatherCondition(daily!!.condition));
            txtMinTemperature!!.text = temperatureFormatter.formatDaily(daily!!.minTemperatureInCelsius, temperatureUnit);
            txtMaxTemperature!!.text = temperatureFormatter.formatDaily(daily!!.maxTemperatureInCelsius, temperatureUnit);
            txtDay!!.text = dateFormatter.formatDayForDaily(daily!!.day as LocalDateTime);
        }
    }

}
package fr.cvlaminck.hwweather.front.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.IconSetManager
import fr.cvlaminck.hwweather.core.managers.UserPreferencesManager
import fr.cvlaminck.hwweather.data.model.weather.CurrentWeatherEntity
import fr.cvlaminck.hwweather.data.model.weather.HourlyForecastEntity
import fr.cvlaminck.hwweather.data.model.weather.WeatherCondition
import fr.cvlaminck.hwweather.front.formatters.DateFormatter
import fr.cvlaminck.hwweather.front.formatters.TemperatureFormatter
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import javax.inject.Inject

public class HourlyForecastView: FrameLayout {
    @Inject
    lateinit var temperatureFormatter: TemperatureFormatter;

    @Inject
    lateinit var dateFormatter: DateFormatter;

    @Inject
    lateinit var iconSetManager: IconSetManager;

    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager;

    private var _hourlyForecast: HourlyForecastEntity? = null;
    var hourlyForecast: HourlyForecastEntity?
            set(forecast: HourlyForecastEntity?) {
                _hourlyForecast = forecast;
                _currentWeather = null;
                updateViews();
            }
            get(): HourlyForecastEntity? = _hourlyForecast;

    private var _currentWeather: CurrentWeatherEntity? = null;
    var currentWeather: CurrentWeatherEntity?
            set(current: CurrentWeatherEntity?) {
                _currentWeather = current;
                _hourlyForecast = null;
                updateViews();
            }
            get(): CurrentWeatherEntity? = _currentWeather;

    private var imgCondition: ImageView? = null;
    private var txtTemperature: TextView? = null;
    private var txtHour: TextView? = null;

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
        (context.applicationContext as HwWeatherApplication).component().inject(this);

        setContentView();
        bindViews();
        updateViews();
    }

    private fun setContentView() {
        val layoutInflater = LayoutInflater.from(context);
        val contextView: ViewGroup = layoutInflater.inflate(R.layout.hourlyforecastview, null) as ViewGroup;
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(contextView, layoutParams);
    }

    private fun bindViews() {
        imgCondition = findViewById(R.id.imgCondition) as ImageView;
        txtTemperature = findViewById(R.id.txtTemperature) as TextView;
        txtHour = findViewById(R.id.txtHour) as TextView;
    }

    private fun emptyViewsContent() {
        imgCondition!!.setImageDrawable(null);
        txtTemperature!!.text = null;
        txtHour!!.text = null;
    }

    private fun updateViews() {
        if (hourlyForecast == null && currentWeather == null) {
            emptyViewsContent()
            return;
        }

        var temperature: Double? = null;
        var condition: WeatherCondition? = null;
        var date: LocalDateTime? = null;
        val temperatureUnit = userPreferencesManager.temperatureUnit;

        if (hourlyForecast != null) {
            temperature = hourlyForecast!!.temperature;
            condition = hourlyForecast!!.condition;
            date = hourlyForecast!!.hour;
        } else if (currentWeather != null) {
            temperature = currentWeather!!.temperature;
            condition = currentWeather!!.condition;
            date = currentWeather!!.hour;
        }

        imgCondition!!.setImageDrawable(iconSetManager.getIconForWeatherCondition(condition as WeatherCondition));
        txtTemperature!!.text = temperatureFormatter.formatDaily(temperature as Double, temperatureUnit);
        txtHour!!.text = dateFormatter.formatHourForHourlyOrCurrent(date as LocalDateTime);
    }

}
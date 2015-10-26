package fr.cvlaminck.hwweather.front.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.core.loaders.HwWeatherOperationResult
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.core.managers.WeatherManager
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import javax.inject.Inject

public class WeatherFragment() : Fragment() {
    companion object {
        private val GET_WEATHER_LOADER_ID = 42;
        private val BUNDLE_CITY = "city";

        private val BUNDLE_ARGUMENTS_CITY = "city";
        fun newInstance(context: Context, city: CityEntity): WeatherFragment {
            val fg = WeatherFragment();
            setCity(fg, city);
            return fg;
        }

        private fun setCity(fg: Fragment, city: CityEntity) {
            val args = Bundle();
            args.putParcelable(BUNDLE_ARGUMENTS_CITY, city);
            fg.arguments = args;
        }
    }

    @Inject
    lateinit var weatherManager: WeatherManager;

    @Inject
    lateinit var cityManager: CityManager;

    private var _city: CityEntity? = null;
    val city: CityEntity
        get(): CityEntity {
            if (_city == null) {
                _city = arguments.getParcelable(BUNDLE_ARGUMENTS_CITY);
            }
            return _city as CityEntity;
        };

    private var _fgWeeklyForecast: WeeklyForecastFragment? = null;
    private val fgWeeklyForecast: WeeklyForecastFragment
        get() {
            if (_fgWeeklyForecast == null) {
                _fgWeeklyForecast = childFragmentManager.findFragmentById(R.id.fgWeeklyForecast) as WeeklyForecastFragment;
            }
            return _fgWeeklyForecast as WeeklyForecastFragment;
        }

    private var _fgHourlyForecast: HourlyForecastFragment? = null;
    private val fgHourlyForecast: HourlyForecastFragment
        get() {
            if (_fgHourlyForecast == null) {
                _fgHourlyForecast = childFragmentManager.findFragmentById(R.id.fgHourlyForecast) as HourlyForecastFragment;
            }
            return _fgHourlyForecast as HourlyForecastFragment;
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _city = null;
        _fgWeeklyForecast = null;
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as HwWeatherApplication).component().inject(this);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.weatherfragment, container, false);

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //TODO: if not results in instance state
        loadWeatherForCity(city, false);
    }

    override fun onSaveInstanceState(out: Bundle) {

    }

    private fun loadWeatherForCity(city: CityEntity, mayRestartIfExisting: Boolean = true) {
        //First, we create or bind to the loader that will do the networking
        val args = Bundle();
        args.putParcelable(BUNDLE_CITY, city);

        when (mayRestartIfExisting) {
            false -> loaderManager.initLoader(GET_WEATHER_LOADER_ID, args, loaderCallbacks);
            true -> loaderManager.restartLoader(GET_WEATHER_LOADER_ID, args, loaderCallbacks);
        }

        //TODO: Then we update the UI to show a indeterminate progress
    }

    private fun updateResults(data: WeatherData) {
        fgHourlyForecast.currentWeather = data.current;
        fgHourlyForecast.hourlyForecasts = data.hourly;
        fgWeeklyForecast.dailyForecasts = data.daily;
    }

    override fun onDetach() {
        super.onDetach()
    }

    private val loaderCallbacks = object : LoaderManager.LoaderCallbacks<HwWeatherOperationResult<WeatherData>> {
        override fun onCreateLoader(id: Int, args: Bundle): Loader<HwWeatherOperationResult<WeatherData>> {
            val city = args.getParcelable<CityEntity>(BUNDLE_CITY);
            val typesToRefresh = WeatherDataType.values.toList();
            return weatherManager.createLoaderForGetWeatherForCity(this@WeatherFragment.context, city, typesToRefresh);
        }

        override fun onLoadFinished(loader: Loader<HwWeatherOperationResult<WeatherData>>, data: HwWeatherOperationResult<WeatherData>) {
            if (!data.failed) {
                updateResults(data.result);
            } else {
                //TODO Handle error
                data.cause.printStackTrace();
            }
        }

        override fun onLoaderReset(loader: Loader<HwWeatherOperationResult<WeatherData>>) {
        }
    }
}
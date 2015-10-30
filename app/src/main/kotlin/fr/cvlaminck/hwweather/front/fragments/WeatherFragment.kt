package fr.cvlaminck.hwweather.front.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import butterknife.ButterKnife
import butterknife.OnClick
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.client.resources.weather.enums.WeatherDataType
import fr.cvlaminck.hwweather.core.loaders.HwWeatherOperationResult
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.core.managers.FavoriteCityManager
import fr.cvlaminck.hwweather.core.managers.WeatherManager
import fr.cvlaminck.hwweather.core.model.weather.WeatherData
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import nl.komponents.kovenant.async
import nl.komponents.kovenant.ui.successUi
import javax.inject.Inject
import kotlinx.android.synthetic.weatherfragment.*;
import nl.komponents.kovenant.android.startKovenant
import nl.komponents.kovenant.android.stopKovenant

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

    @Inject
    lateinit var favoriteCityManager: FavoriteCityManager;

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
            return _fgWeeklyForecast as WeeklyForecastFragment;
        }

    private var _fgHourlyForecast: HourlyForecastFragment? = null;
    private val fgHourlyForecast: HourlyForecastFragment
        get() {
            return _fgHourlyForecast as HourlyForecastFragment;
        }

    var hourlyForecastContentOffset: Float = 0f
        set(offset: Float) {
            Log.d("weather", this.toString() + " set");

            field = offset;
            updateHourlyForecastContentOffset();
        }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity);
        (activity.application as HwWeatherApplication).component().inject(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        startKovenant();

        _city = null;
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.weatherfragment, container, false);

        _fgHourlyForecast = HourlyForecastFragment();
        _fgWeeklyForecast = WeeklyForecastFragment();

        val ft = childFragmentManager.beginTransaction();
        ft.add(R.id.flWeeklyForecast, _fgWeeklyForecast);
        ft.add(R.id.flHourlyForecast, _fgHourlyForecast);
        ft.commit();

        ButterKnife.bind(this, view);
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnFavorite.isChecked = favoriteCityManager.isFavorite(city);

        if (savedInstanceState != null) {
            // We reload the city from the parcelable so we keep its id if we have retrieved it during the refresh
            _city = savedInstanceState.getParcelable(BUNDLE_CITY);
        }

        updateViews();
        updateHourlyForecastContentOffset();

        loadWeatherForCity(city, false);
    }

    override fun onSaveInstanceState(out: Bundle) {
        out.putParcelable(BUNDLE_CITY, _city);
    }

    override fun onDestroy() {
        super.onDestroy();
        stopKovenant();
    }

    private fun updateHourlyForecastContentOffset() {
        Log.d("weather", this.toString() + " updateHourlyForecastContentOffset: " + hourlyForecastContentOffset);
        if (_fgHourlyForecast != null) {
            _fgHourlyForecast!!.circleSliderInnerContentOffset = hourlyForecastContentOffset;
        }
    }

    private fun updateViews() {
        btnFavorite.isEnabled = (city.id != null);
    }

    @OnClick(R.id.btnFavorite)
    fun btnFavoriteClick(btnFavorite: ToggleButton) {
        val checked = btnFavorite.isChecked;
        async {
            if (checked) {
                favoriteCityManager.add(city);
            } else {
                favoriteCityManager.remove(city);
            }
        } successUi {
            btnFavorite.isChecked = checked;
        }
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
        _city = data.city as CityEntity;
        fgHourlyForecast.currentWeather = data.current;
        fgHourlyForecast.hourlyForecasts = data.hourly;
        fgWeeklyForecast.dailyForecasts = data.daily;

        updateViews();
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
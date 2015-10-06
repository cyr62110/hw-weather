package fr.cvlaminck.hwweather.front.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.core.managers.WeatherManager
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import javax.inject.Inject

public class WeatherFragment() : Fragment() {
    companion object {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _city = null;
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as HwWeatherApplication).component().inject(this);
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.weatherfragment, container, false);

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onDetach() {
        super.onDetach()
    }
}
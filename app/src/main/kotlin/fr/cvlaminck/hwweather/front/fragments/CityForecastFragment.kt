package fr.cvlaminck.hwweather.front.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.City

public class CityForecastFragment : Fragment() {

    companion object {
        fun newInstance(city: City) : Fragment {
            return CityForecastFragment();
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.cityforecastfragment, container, false);
    }
}
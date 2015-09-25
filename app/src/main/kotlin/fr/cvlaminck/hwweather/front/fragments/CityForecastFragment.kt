package fr.cvlaminck.hwweather.front.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.CityEntity

public class CityForecastFragment : Fragment() {
    companion object {
        fun newInstance(city: CityEntity): Fragment {
            return CityForecastFragment();
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.cityforecastfragment, container, false);

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onDetach() {
        super.onDetach()
    }
}
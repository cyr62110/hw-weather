package fr.cvlaminck.hwweather.front.fragments

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.services.ForecastService
import fr.cvlaminck.hwweather.core.services.impl.ForecastServiceImpl
import fr.cvlaminck.hwweather.data.model.CityEntity

public class CityForecastFragment : Fragment() {
    companion object {
        fun newInstance(city: CityEntity): Fragment {
            return CityForecastFragment();
        }
    }

    private var forecastService: ForecastService? = null;
    private var forecastServiceConnection: ForecastServiceConnection? = null;

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (forecastServiceConnection == null) {
            forecastServiceConnection = ForecastServiceConnection();
            val bindIntent = Intent(activity, javaClass<ForecastServiceImpl>);
            activity!!.bindService(bindIntent, forecastServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.cityforecastfragment, container, false);

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onDetach() {
        super.onDetach()
    }

    private class ForecastServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            throw UnsupportedOperationException()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            throw UnsupportedOperationException()
        }
    }
}
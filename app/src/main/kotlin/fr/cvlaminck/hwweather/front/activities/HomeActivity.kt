package fr.cvlaminck.hwweather.front.activities

import android.app.Activity
import android.os.Bundle

import fr.cvlaminck.hwweather.R

public class HomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
    }
}
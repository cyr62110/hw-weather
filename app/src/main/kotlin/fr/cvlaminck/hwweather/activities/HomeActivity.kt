package fr.cvlaminck.hwweather.activities

import android.app.Activity
import android.os.Bundle
import fr.cvlaminck.hwweather.R

class HomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
    }
}
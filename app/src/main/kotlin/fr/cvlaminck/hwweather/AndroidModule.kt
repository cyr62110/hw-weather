package fr.cvlaminck.hwweather

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
public class AndroidModule(private val application: Application) {

    @Provides @Singleton fun provideApplicationContext() : Context {
        return application.getApplicationContext();
    }

    @Provides @Singleton fun provideLayoutInflater() : LayoutInflater {
        return LayoutInflater.from(application.getApplicationContext());
    }

}
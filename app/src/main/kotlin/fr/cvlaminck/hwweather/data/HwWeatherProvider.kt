package fr.cvlaminck.hwweather.data

import android.net.Uri
import net.simonvt.schematic.annotation.ContentProvider
import net.simonvt.schematic.annotation.ContentUri
import net.simonvt.schematic.annotation.TableEndpoint

@ContentProvider(authority = HwWeatherProvider.authority, database = HwWeatherDatabase::class)
public object HwWeatherProvider {

    val authority = "fr.cvlaminck.hwweather";

    @TableEndpoint(table = HwWeatherDatabase.cities)
    object Cities {
        @ContentUri(
                path = HwWeatherDatabase.cities,
                type = "vnd.android.cursor.dir/${HwWeatherDatabase.cities}"
        )
        val cities = Uri.parse("content://${authority}/${HwWeatherDatabase.cities}");
    }

}
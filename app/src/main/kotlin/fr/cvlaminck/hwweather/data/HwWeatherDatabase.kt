package fr.cvlaminck.hwweather.data

import fr.cvlaminck.hwweather.data.tables.CityTableColumns
import net.simonvt.schematic.annotation.Database
import net.simonvt.schematic.annotation.ExecOnCreate
import net.simonvt.schematic.annotation.Table

@Database(version = HwWeatherDatabase.version)
public object HwWeatherDatabase {

    val version = 1;

    @Table(CityTableColumns::class) val cities = "cities";

    @ExecOnCreate val insertDemoCity = "INSERT INTO ${cities}(name, country) VALUES('Paris', 'France');";
}
package fr.cvlaminck.hwweather.data.tables

import net.simonvt.schematic.annotation.AutoIncrement
import net.simonvt.schematic.annotation.DataType
import net.simonvt.schematic.annotation.PrimaryKey

public object CityTableColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement @publicField
    val _id = "_id";

    @DataType(DataType.Type.TEXT) @publicField
    val name = "name";

    @DataType(DataType.Type.TEXT) @publicField
    val county = "county";

    @DataType(DataType.Type.TEXT) @publicField
    val state = "state";

    @DataType(DataType.Type.TEXT) @publicField
    val country = "country";

    val projection = arrayOf(_id, name, county, state, country);
}
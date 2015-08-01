package fr.cvlaminck.hwweather.data.model

import android.database.Cursor
import fr.cvlaminck.hwweather.data.tables.CityTableColumns

public class CityEntity(
        val name: String,
        val county: String?,
        val state: String?,
        val country: String
) {
    companion object {
        fun fromCursor(cursor: Cursor) : CityEntity {
            val name = cursor.getString(cursor.getColumnIndex(CityTableColumns.name));
            val county = cursor.getString(cursor.getColumnIndex(CityTableColumns.county));
            val state = cursor.getString(cursor.getColumnIndex(CityTableColumns.state));
            val country = cursor.getString(cursor.getColumnIndex(CityTableColumns.country));
            return CityEntity(name, county, state, country);
        }
    }
}
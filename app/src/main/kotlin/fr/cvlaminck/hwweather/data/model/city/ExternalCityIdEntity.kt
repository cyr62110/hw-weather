package fr.cvlaminck.hwweather.data.model.city

import android.os.Parcel
import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.data.dao.city.ExternalCityIdRepository

@DatabaseTable(tableName = "external_city_id", daoClass = ExternalCityIdRepository::class)
public class ExternalCityIdEntity public constructor() : Parcelable {

    private constructor(source: Parcel) : this() {
        if (source.readInt() == 1) {
            id = source.readInt();
        }
        provider = source.readString();
        externalId = source.readString();
    }

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var city: CityEntity? = null;

    @DatabaseField(uniqueCombo = true)
    var provider: String? = null;

    @DatabaseField(uniqueCombo = true)
    var externalId: String? = null;

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if (id != null) 1 else 0);
        if (id != null) {
            dest.writeInt(id as Int);
        }
        dest.writeString(provider);
        dest.writeString(externalId);
    }

    override fun describeContents(): Int = 0;

    companion object {
        val CREATOR = object : Parcelable.Creator<ExternalCityIdEntity> {
            override fun createFromParcel(source: Parcel): ExternalCityIdEntity = ExternalCityIdEntity(source);
            override fun newArray(size: Int): Array<ExternalCityIdEntity?> = arrayOfNulls<ExternalCityIdEntity?>(size);
        };
    }
}
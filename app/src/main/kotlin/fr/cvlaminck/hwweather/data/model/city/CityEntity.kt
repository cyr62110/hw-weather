package fr.cvlaminck.hwweather.data.model.city

import android.os.Parcel
import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import fr.cvlaminck.hwweather.client.resources.CityResource
import fr.cvlaminck.hwweather.data.dao.city.CityRepository

@DatabaseTable(tableName = "city", daoClass = CityRepository::class)
public class CityEntity public constructor() : Parcelable {

    public constructor(resource: CityResource) : this() {
        serverId = resource.id;
        resource.externalId
        name = resource.name;
        country = resource.country;
    }

    public constructor(source: Parcel) : this() {
        if (source.readInt() == 1) {
            id = source.readInt();
        }
        if (source.readInt() == 1) {
            serverId = source.readString();
        }
        if (source.readInt() == 1) {
            serverExternalId = source.readParcelable(ExternalCityIdEntity::class.java.classLoader);
        }
        name = source.readString();
        country = source.readString();
    }

    @DatabaseField(generatedId = true)
    var id: Int? = null;

    @DatabaseField
    var serverId: String? = null;

    var serverExternalId: ExternalCityIdEntity? = null;

    @DatabaseField
    var name: String? = null;

    @DatabaseField
    var country: String? = null;

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if (id != null) 1 else 0);
        if (id != null) {
            dest.writeInt(id as Int);
        }
        dest.writeInt(if (serverId != null) 1 else 0)
        if (serverId != null) {
            dest.writeString(serverId);
        }
        dest.writeInt(if (serverExternalId != null) 1 else 0);
        if (serverExternalId != null) {
            dest.writeParcelable(serverExternalId, flags);
        }
        dest.writeString(name);
        dest.writeString(country);
    }

    override fun hashCode(): Int {
        return if (id != null) id as Int else super.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is CityEntity) {
            return false;
        }
        return if (id != null) {
            id == other.id;
        } else {
            super.equals(other);
        }
    }

    override fun describeContents(): Int = 0;

    companion object {
        val CREATOR = object : Parcelable.Creator<CityEntity> {
            override fun createFromParcel(source: Parcel): CityEntity = CityEntity(source);
            override fun newArray(size: Int): Array<CityEntity?> = arrayOfNulls<CityEntity?>(size);
        };
    }
}
package fr.cvlaminck.hwweather.core.model.city

import android.os.Parcel
import android.os.Parcelable
import fr.cvlaminck.hwweather.core.model.Page
import fr.cvlaminck.hwweather.core.model.PageSet
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import java.util.*

public class CityPageSet(
        val query: String,
        numberOfResultPerPage: Int,
        totalNumberOfResult: Int)
: PageSet<CityEntity> (numberOfResultPerPage, totalNumberOfResult), Parcelable {

    constructor(
            query: String,
            page: Page<CityEntity>)
    : this(query, page.numberOfResultPerPage, page.totalNumberOfResult) {
        append(page);
    }



    companion object {
        val CREATOR = object : Parcelable.Creator<CityPageSet> {
            override fun createFromParcel(source: Parcel): CityPageSet? {
                val pageSet = CityPageSet (
                        source.readString(),
                        source.readInt(),
                        source.readInt()
                );
                val numberOfPage = source.readInt();
                if (numberOfPage > 0) {
                    val pageIndexes = IntArray(numberOfPage);
                    source.readIntArray(pageIndexes);
                    pageSet.pageIndexes.addAll(pageIndexes.toList());
                }
                val results = ArrayList<CityEntity>();
                source.readTypedList(results, CityEntity.CREATOR);
                pageSet.results.addAll(results);
                return pageSet;
            }
            override fun newArray(size: Int): Array<out CityPageSet>? = arrayOfNulls<CityPageSet?>(size) as Array<out CityPageSet>?;
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(query);
        dest?.writeInt(numberOfResultPerPage);
        dest?.writeInt(totalNumberOfResult);
        dest?.writeInt(pageIndexes.size());
        if (pageIndexes.size() > 0) {
            dest?.writeIntArray(pageIndexes.toIntArray());
        }
        if (results.size() > 0) {
            dest?.writeTypedList(results);
        }
    }

    override fun describeContents(): Int = 0;
}
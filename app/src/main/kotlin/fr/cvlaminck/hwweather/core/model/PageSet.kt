package fr.cvlaminck.hwweather.core.model

import android.os.Parcelable
import android.util.SparseIntArray
import java.util.*

public open class PageSet<T : Parcelable> protected constructor(
        protected val numberOfResultPerPage: Int,
        protected val totalNumberOfResult: Int
) {

    protected val pageIndexes: MutableList<Int> = arrayListOf();
    val results: MutableList<T> = arrayListOf();

    private val lastPageIndex: Int
        get() = pageIndexes.max() as Int;

    fun hasNext(): Boolean = lastPageIndex * numberOfResultPerPage < totalNumberOfResult;

    fun append(page: Page<T>) {
        pageIndexes += page.pageIndex;
        results += page.results;
    }
}
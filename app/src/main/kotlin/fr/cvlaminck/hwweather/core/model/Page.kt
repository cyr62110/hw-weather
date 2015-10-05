package fr.cvlaminck.hwweather.core.model

public class Page<T>(
        val numberOfResultPerPage: Int,
        val pageIndex: Int,
        val totalNumberOfResult: Int,
        val results: Collection<T>
) {
}
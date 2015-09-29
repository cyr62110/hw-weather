package fr.cvlaminck.hwweather.core.model

public class Page<T>
(
    val numberOfResultPerPage: Int,
    val page: Int,
    val totalNumberOfResult: Int,
    val results: Collection<T>
) {



}
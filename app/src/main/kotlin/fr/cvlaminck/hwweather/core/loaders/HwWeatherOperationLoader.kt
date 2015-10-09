package fr.cvlaminck.hwweather.core.loaders

import android.content.AsyncTaskLoader
import android.content.Context
import android.content.Loader

public abstract class HwWeatherOperationLoader<T> (
        context: Context
): AsyncTaskLoader<HwWeatherOperationResult<T>> (context) {

    private var done: Boolean = false;
    private var result: HwWeatherOperationResult<T>? = null;

    override fun loadInBackground(): HwWeatherOperationResult<T> {
        return try {
            val requestResult = doRequest();
            return HwWeatherOperationResult(requestResult);
        } catch (ex: Exception) {
            return HwWeatherOperationResult(ex);
        }
    }

    abstract fun doRequest(): T;

    override fun onStartLoading() {
        if (result != null) {
            deliverResult(result);
        }
        if (result == null) {
            forceLoad();
        }
    }

    override fun onStopLoading() {
        cancelLoad();
    }
}

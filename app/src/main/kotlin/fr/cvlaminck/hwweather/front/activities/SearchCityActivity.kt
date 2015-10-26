package fr.cvlaminck.hwweather.front.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.View
import android.widget.SearchView
import com.skocken.efficientadapter.lib.adapter.EfficientAdapter
import com.skocken.efficientadapter.lib.adapter.EfficientRecyclerAdapter
import fr.cvlaminck.hwweather.HwWeatherApplication
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.core.loaders.HwWeatherOperationResult
import fr.cvlaminck.hwweather.core.loaders.city.SearchCityLoader
import fr.cvlaminck.hwweather.core.managers.CityManager
import fr.cvlaminck.hwweather.core.model.Page
import fr.cvlaminck.hwweather.core.model.city.CityPageSet
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.front.viewholders.CityViewHolder
import kotlinx.android.synthetic.searchcityactivity.rvCities
import kotlinx.android.synthetic.searchcityactivity.svCity
import javax.inject.Inject

public class SearchCityActivity : FragmentActivity() {
    companion object {
        val BUNDLE_QUERY = "QUERY";
        val BUNDLE_SEARCHING = "SEARCHING";
        val BUNDLE_RESULTS = "RESULTS";

        val SEARCH_CITY_LOADER_ID = 42;
    }

    @Inject
    lateinit var cityManager: CityManager;

    private var searching: Boolean = false;
    private var results: CityPageSet? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as HwWeatherApplication).component().inject(this);
        setContentView(R.layout.searchcityactivity);

        svCity.setOnQueryTextListener(svCityOnQueryTextListener);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(BUNDLE_QUERY, svCity.query.toString());
        outState.putBoolean(BUNDLE_SEARCHING, searching);
        if (results != null) {
            outState.putParcelable(BUNDLE_RESULTS, results);
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        svCity.setQuery(savedInstanceState.getString(BUNDLE_QUERY, ""), false);
        searching = savedInstanceState.getBoolean(BUNDLE_SEARCHING, false);
        if (searching) {
            searchCity(svCity.query.toString(), false);
        } else {
            if (savedInstanceState.containsKey(BUNDLE_RESULTS)) {
                updateResults(savedInstanceState.getParcelable(BUNDLE_RESULTS));
            }
        }
    }

    private fun searchCity(query: String, mayRestartIfExisting: Boolean = true) {
        //First, we create or bind to the loader that will do the networking
        val args = Bundle();
        args.putString(BUNDLE_QUERY, query);

        when (mayRestartIfExisting) {
            false -> supportLoaderManager.initLoader(SEARCH_CITY_LOADER_ID, args, loaderCallbacks);
            true -> supportLoaderManager.restartLoader(SEARCH_CITY_LOADER_ID, args, loaderCallbacks);
        }

        //TODO: Then we update the UI to show a indeterminate progress
    }

    private fun updateResults(query: String, page: Page<CityEntity>) {
        if (results != null && query == results!!.query) {
            results!!.append(page);
            (rvCities.adapter as EfficientAdapter<CityEntity>).addAll(page.results);
        } else {
            val pageSet = CityPageSet(query, page);
            updateResults(pageSet);
        }
    }

    private fun updateResults(results: CityPageSet) {
        this.results = results;
        val adapter = EfficientRecyclerAdapter<CityEntity>(R.layout.cityview, CityViewHolder::class.java, results.results);
        adapter.setOnItemClickListener(rvCitiesOnItemClickListener);
        rvCities.adapter = adapter;
    }

    private fun clearResults() {
        rvCities.adapter = null;
        //TODO Show the empty view
    }

    private val loaderCallbacks = object: LoaderManager.LoaderCallbacks<HwWeatherOperationResult<Page<CityEntity>>> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<HwWeatherOperationResult<Page<CityEntity>>> {
            val query = args!!.getString(BUNDLE_QUERY);
            val loader = cityManager.createLoaderForSearch(this@SearchCityActivity, query);
            return loader;
        }

        override fun onLoadFinished(loader: Loader<HwWeatherOperationResult<Page<CityEntity>>>, data: HwWeatherOperationResult<Page<CityEntity>>) {
            searching = false;
            if (!data.failed) {
                val query = (loader as SearchCityLoader).query;
                updateResults(query, data.result);
            } else {
                //TODO Handle error
                data.cause.printStackTrace();
            }
        }

        override fun onLoaderReset(loader: Loader<HwWeatherOperationResult<Page<CityEntity>>>) {
        }
    }

    private val rvCitiesOnItemClickListener = object: EfficientAdapter.OnItemClickListener<CityEntity> {
        override fun onItemClick(adapter: EfficientAdapter<CityEntity>, view: View, city: CityEntity, position: Int) {
            val intent = Intent(this@SearchCityActivity, WeatherActivity::class.java);
            intent.putExtra(WeatherActivity.INTENT_CITY, city);
            startActivity(intent);
        }
    }

    private val svCityOnQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            //TODO For now do nothing, maybe autocompletion later
            return true;
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null && !query.isEmpty()) {
                searchCity(query);
            } else {
                loaderManager.destroyLoader(SEARCH_CITY_LOADER_ID);
                clearResults();
            }
            return true;
        }
    }
}
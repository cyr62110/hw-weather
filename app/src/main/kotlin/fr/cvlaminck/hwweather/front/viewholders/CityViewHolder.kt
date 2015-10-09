package fr.cvlaminck.hwweather.front.viewholders

import android.content.Context
import android.view.View
import android.widget.TextView
import com.skocken.efficientadapter.lib.viewholder.EfficientViewHolder
import fr.cvlaminck.hwweather.R
import fr.cvlaminck.hwweather.data.model.city.CityEntity

public class CityViewHolder(
        itemView: View
): EfficientViewHolder<CityEntity>(itemView) {

    override fun updateView(context: Context, city: CityEntity) {
        val txtCityName: TextView = findViewByIdEfficient(R.id.txtCityName);
        val txtCityCountry: TextView = findViewByIdEfficient(R.id.txtCityCountry);

        txtCityName.text = city.name;
        txtCityCountry.text = city.country;
    }

    override fun isClickable(): Boolean = true;
    override fun isLongClickable(): Boolean = true;
}
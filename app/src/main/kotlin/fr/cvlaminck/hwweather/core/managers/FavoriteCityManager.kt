package fr.cvlaminck.hwweather.core.managers

import fr.cvlaminck.hwweather.data.dao.city.FavoriteCityRepository
import fr.cvlaminck.hwweather.data.model.city.CityEntity
import fr.cvlaminck.hwweather.data.model.city.FavoriteCityEntity
import javax.inject.Inject

public class FavoriteCityManager @Inject public constructor(
        val favoriteCityRepository: FavoriteCityRepository
) {

    val hasFavorite: Boolean
        get() = favoriteCityRepository.countOf() > 0;

    val favorites: List<FavoriteCityEntity>
        get() = favoriteCityRepository.findFavoritesOrdered();

    val orderedFavoriteCities: List<CityEntity>
        get() = favorites.map { it.city as CityEntity };

    fun add(city: CityEntity) {
        val favorites = this.orderedFavoriteCities;
        if (favorites.contains(city)) {
            return;
        }

        val favoriteCity = FavoriteCityEntity();
        favoriteCity.city = city;
        favoriteCity.order = favorites.size + 1;
        favoriteCityRepository.create(favoriteCity);
    }

    fun remove(city: CityEntity) {
        val favorites = this.favorites.toArrayList();
        val favoriteEntity = favorites.find { city.equals(it.city) };
        if (favoriteEntity == null) {
            return;
        }

        favorites.remove(favoriteEntity);
        favorites
                .mapIndexed { index, favoriteCityEntity -> favoriteCityEntity.order = index + 1; favoriteCityEntity }
                .forEach { favoriteCityRepository.update(it); } // Bad :(, i know but no update in list in ormlite.
    }
}
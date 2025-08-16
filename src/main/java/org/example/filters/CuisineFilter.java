package org.example.filters;

import org.example.enums.Cuisine;
import org.example.models.Restaurant;

public class CuisineFilter implements Filter {
    private final Cuisine[] cuisines;

    public CuisineFilter(Cuisine... cuisines) {
        this.cuisines = cuisines;
    }

    public boolean apply(Restaurant restaurant) {
        for (Cuisine cuisine : cuisines) {
            if (restaurant.getCuisines().contains(cuisine)) {
                return true;
            }
        }
        return false;
    }
}

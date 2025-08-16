package org.example.filters;

import org.example.models.Restaurant;

public class VegFilter implements Filter {
    private boolean isVeg;

    public VegFilter(boolean isVeg) {
        this.isVeg = isVeg;
    }

    @Override
    public boolean apply(Restaurant restaurant) {
        return restaurant.isVeg() == isVeg;
    }
}

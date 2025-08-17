package org.example.filters;

import org.example.models.Restaurant;

public class NameFilter implements Filter {
    private String restaurantName;

    public NameFilter (String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean apply (Restaurant restaurant) {
        return restaurantName.equals(restaurant.getName());
    }
}

package org.example.filters;

import org.example.models.Restaurant;

public class CityFilter implements Filter {
    private final String city;

    public CityFilter(String city) {
        this.city = city;
    }

    @Override
    public boolean apply(Restaurant restaurant) {
        return city.equalsIgnoreCase(restaurant.getAddress().getCity());
    }
}

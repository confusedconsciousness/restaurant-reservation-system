package org.example.filters;

import org.example.enums.Cuisine;
import org.example.models.Restaurant;

import java.util.List;

public class CuisineFilter implements Filter {
    public List<Restaurant> applyFilter(List<Restaurant> restaurants, String cuisine) {
        return restaurants.stream().filter(r -> !r.getCuisines().isEmpty() && r.getCuisines().contains(Cuisine.valueOf(cuisine.toUpperCase())))
                .toList();
    }

    public String getName() {
        return "cuisine";
    }
}

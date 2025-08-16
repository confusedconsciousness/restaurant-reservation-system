package org.example.filters;

import org.example.models.Restaurant;

import java.util.List;

public class NameFilter implements Filter {

    @Override
    public List<Restaurant> applyFilter(List<Restaurant> restaurants, String value) {
        return restaurants.stream().filter(r -> r.getName() != null && r.getName().contains(value)).toList();
    }

    public String getName() {
        return "name";
    }
}

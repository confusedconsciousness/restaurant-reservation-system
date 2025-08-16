package org.example.filters;

import org.example.models.Restaurant;

import java.util.List;

public interface Filter {
    List<Restaurant> applyFilter(List<Restaurant> restaurants, String value);
    String getName();
}

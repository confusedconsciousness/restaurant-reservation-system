package org.example.filters;

import org.example.models.Restaurant;

import java.util.List;

public interface Filter {
    boolean apply(Restaurant restaurant);
}

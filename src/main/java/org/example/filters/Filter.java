package org.example.filters;

import org.example.models.Restaurant;

public interface Filter {
    boolean apply (Restaurant restaurant);
}

package org.example.filters;

import org.example.models.Restaurant;

public class AndFilter implements Filter {
    private final Filter[] filters;

    public AndFilter(Filter... filters) {
        this.filters = filters;
    }

    @Override
    public boolean apply(Restaurant restaurant) {
        for (Filter filter : filters) {
            if (!filter.apply(restaurant)) return false;
        }
        return true;
    }
}

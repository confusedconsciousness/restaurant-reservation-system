package org.example.repositories;

import org.example.models.Restaurant;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final AtomicInteger counter = new AtomicInteger(1);

    private final Map<String, Restaurant> restaurants = new ConcurrentHashMap<>();

    @Override
    public Restaurant saveRestaurant (Restaurant restaurant) {
        restaurant.setId(generateRestaurantId());
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    @Override
    public Restaurant getRestaurantById (String restaurantId) {
        return restaurants.get(restaurantId);
    }

    @Override
    public Restaurant updateRestaurant (Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants () {
        return restaurants.values().stream().toList();
    }

    @Override
    public void deleteRestaurant (String restaurantId) {
        restaurants.remove(restaurantId);
    }

    private String generateRestaurantId () {
        return "R" + counter.getAndIncrement();
    }
}

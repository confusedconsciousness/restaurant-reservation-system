package org.example.repositories;

import org.example.models.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(String restaurantId);

    Restaurant updateRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurants();

    void deleteRestaurant(String restaurantId);

    void updateTimeSlot(String restaurantId, double timeSlot);
}

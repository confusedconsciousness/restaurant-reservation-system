package org.example.services;

import org.example.enums.Cuisine;
import org.example.filters.CuisineFilter;
import org.example.filters.Filter;
import org.example.filters.NameFilter;
import org.example.models.Address;
import org.example.models.Restaurant;
import org.example.repositories.RestaurantRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant registerRestaurant(String name, Set<Cuisine> cuisines,
                                         String street,
                                         String city,
                                         String state,
                                         String zip,
                                         String phoneNumber,
                                         double costOfTwo,
                                         String openingHour,
                                         String closingHour) {
        // we can check if this restaurant already exists in the database
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or empty");
        }
        // more checks on phone number and address can be added here (i am skipping as of now due to time constraints)
        Restaurant restaurant = new Restaurant();
        Cuisine defaultCuisine = Cuisine.INDIAN; // default cuisine
        if (cuisines == null || cuisines.isEmpty()) {
            // we can use a default cuisine
            cuisines = Set.of(defaultCuisine);
        }
        restaurant.setName(name);
        restaurant.setCuisines(cuisines);
        restaurant.setAddress(new Address(street, city, state, zip));
        restaurant.setPhoneNumber(phoneNumber);
        restaurant.setCostOfTwo(costOfTwo);
        restaurant.setOpeningHour(openingHour);
        restaurant.setClosingHour(closingHour);
        return repository.saveRestaurant(restaurant);
    }

    public void updateTimeSlots(String restaurantId, String date, String time, int numberOfTables) {
        Restaurant restaurant = repository.getRestaurantById(restaurantId);
        if (restaurant != null) {
            restaurant.addSlot(date, time, numberOfTables);
        }
    }

    public List<Restaurant> searchRestaurant(Map<String, String> searchParams) {
        List<Restaurant> restaurants = repository.getAllRestaurants();
        if (searchParams == null || searchParams.isEmpty()) {
            return restaurants;
        }
        List<Filter> filters = List.of(new CuisineFilter(), new NameFilter());
        for (Filter filter : filters) {
            if (searchParams.containsKey(filter.getName())) {
                restaurants = filter.applyFilter(restaurants, searchParams.get(filter.getName()));
            }
        }
        return restaurants;
    }

    public Restaurant getRestaurantById(String id) {
        return repository.getRestaurantById(id);
    }
}

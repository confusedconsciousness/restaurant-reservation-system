package org.example;

import org.example.enums.Cuisine;
import org.example.models.Restaurant;
import org.example.repositories.RestaurantRepository;
import org.example.repositories.RestaurantRepositoryImpl;
import org.example.services.RestaurantService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        RestaurantRepository repository = new RestaurantRepositoryImpl();
        RestaurantService restaurantService = new RestaurantService(repository);

        System.out.println("################ REGISTER RESTAURANTS ################################");
        //  let's add  a dummy restaurant
        Restaurant roxie = restaurantService.registerRestaurant("Roxie",
                Set.of(Cuisine.ITALIAN),
                "Harlur",
                "Bangalore",
                "Karnataka",
                "560102", "123456789",
                2341.2,
                10,
                10,
                22);
        System.out.println("Successfully registered restaurant: " + roxie);
        //  let's add another restaurant
        Restaurant bierLibrary = restaurantService.registerRestaurant("Bier Library",
                Set.of(Cuisine.INDIAN, Cuisine.FRENCH, Cuisine.CHINESE),
                "Whitefield",
                "Bangalore",
                "Karnataka",
                "560106", "251251242",
                1234.5,
                5,
                13,
                20);

        System.out.println("Successfully registered restaurant: " + bierLibrary);

        System.out.println("################ SEARCH RESTAURANTS ################################");

        List<Restaurant> foundRestaurants = restaurantService.searchRestaurant(Map.of("cuisine", Cuisine.ITALIAN.getDisplayName(), "name", "Roxie"));
        if (foundRestaurants.isEmpty()) {
            System.out.println("No restaurants found with the specified criteria.");
        } else {
            System.out.println("Found Restaurants:");
            for (Restaurant restaurant : foundRestaurants) {
                System.out.println(restaurant);
            }
        }

        foundRestaurants = restaurantService.searchRestaurant(Map.of("cuisine", Cuisine.THAI.getDisplayName()));
        if (foundRestaurants.isEmpty()) {
            System.out.println("No restaurants found with the specified criteria.");
        } else {
            System.out.println("Found Restaurants:");
            for (Restaurant restaurant : foundRestaurants) {
                System.out.println(restaurant);
            }
        }


        System.out.println("################ BOOK A TABLE ################################");

        // lets' reserve a table
        // R1 is roxie
        boolean booked = restaurantService.bookTable("R1", "17-08-2025", 12, 5);
        if (booked) {
            System.out.println("Table booked successfully at Roxie for 5 people on 17-08-2023 at 12:00.");
        } else {
            System.out.println("Failed to book table at Roxie. Please try again later.");
        }
        booked = restaurantService.bookTable("R2", "17-08-2025", 12, 5);
    }
}
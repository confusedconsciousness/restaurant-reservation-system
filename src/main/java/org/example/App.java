package org.example;

import org.example.enums.Cuisine;
import org.example.filters.*;
import org.example.models.Restaurant;
import org.example.repositories.BookingRepository;
import org.example.repositories.BookingRepositoryImpl;
import org.example.repositories.RestaurantRepository;
import org.example.repositories.RestaurantRepositoryImpl;
import org.example.services.BookingService;
import org.example.services.RestaurantService;

import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        RestaurantRepository repository = new RestaurantRepositoryImpl();
        BookingRepository bookingRepository = new BookingRepositoryImpl();
        RestaurantService restaurantService = new RestaurantService(repository);
        BookingService bookingService = new BookingService(restaurantService, bookingRepository);

        System.out.println("################ REGISTER RESTAURANTS ################################");
        //  let's add  a dummy restaurant
        Restaurant roxie = restaurantService.registerRestaurant("Roxie",
                Set.of(Cuisine.ITALIAN),
                true,
                "Harlur",
                "Bangalore",
                "Karnataka",
                "560102", "123456789",
                2341.2,
                "10:00",
                "22:00");
        roxie.addSlot("17-08-2025", "12:00", 10);
        roxie.addSlot("17-08-2025", "13:00", 10);
        System.out.println("Successfully registered restaurant: " + roxie);
        //  let's add another restaurant
        Restaurant bierLibrary = restaurantService.registerRestaurant("Bier Library",
                Set.of(Cuisine.INDIAN, Cuisine.FRENCH, Cuisine.CHINESE),
                false,
                "Whitefield",
                "Bangalore",
                "Karnataka",
                "560106", "251251242",
                1234.5,
                "13:00",
                "20:00");
        bierLibrary.addSlot("17-08-2025", "14:00", 5);
        bierLibrary.addSlot("17-08-2025", "19:00", 5);
        System.out.println("Successfully registered restaurant: " + bierLibrary);

        System.out.println("################ SEARCH RESTAURANTS ################################");

        Filter filter = new AndFilter(new CityFilter("bangalore"), new VegFilter(false));
        List<Restaurant> foundRestaurants = restaurantService.searchRestaurant(filter);
        if (foundRestaurants.isEmpty()) {
            System.out.println("No restaurants found with the specified criteria.");
        } else {
            System.out.println("Found Restaurants:");
            for (Restaurant restaurant : foundRestaurants) {
                System.out.println(restaurant);
            }
        }

        filter = new CuisingFilter(Cuisine.AMERICAN);
        foundRestaurants = restaurantService.searchRestaurant(filter);
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
        bookingService.bookTable("u1", "R1", "17-08-2025", "12:00", 5).ifPresentOrElse((booking -> {
            System.out.println("Table booked successfully: " + booking);
        }), () -> {
            System.out.println("Failed to book table at Roxie. Please try again later.");
        });

        bookingService.bookTable("u2", "R1", "17-08-2025", "12:00", 6).ifPresentOrElse((booking -> {
            System.out.println("Table booked successfully: " + booking);
        }), () -> {
            System.out.println("Failed to book table at BierLibary. Please try again later.");
        });

    }
}
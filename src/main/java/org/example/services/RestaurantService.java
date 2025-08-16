package org.example.services;

import lombok.SneakyThrows;
import org.example.enums.Cuisine;
import org.example.filters.CuisineFilter;
import org.example.filters.Filter;
import org.example.filters.NameFilter;
import org.example.models.Address;
import org.example.models.Restaurant;
import org.example.repositories.RestaurantRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RestaurantService {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
                                         int seatingCapacity,
                                         int openingHour,
                                         int closingHour) {
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
        restaurant.setSeatingCapacity(seatingCapacity);
        restaurant.setOpeningHour(openingHour);
        restaurant.setClosingHour(closingHour);

        // since we are registering the restaurant, we need to initialise the slot availability
        Map<Integer, Integer> slots = generateSlots(openingHour, closingHour, seatingCapacity);
        Map<String, Map<Integer, Integer>> slotAvailability = new ConcurrentHashMap<>();
        slotAvailability.put(sdf.format(new Date()), slots);
        restaurant.setSlotAvailability(slotAvailability);

        return repository.saveRestaurant(restaurant);
    }

    private Map<Integer, Integer> generateSlots(int openingHour, int closingHour, int seatingCapacity) {
        Map<Integer, Integer> slots = new ConcurrentHashMap<>();
        for (int i = openingHour; i < closingHour; i++) {
            slots.put(i, seatingCapacity); // all slots are available initially
        }
        return slots;
    }

    private boolean updateTimeSlots(String restaurantId, String date, int timeSlot, int numberOfSeats) {
        return true;

    }

    @SneakyThrows
    public synchronized boolean bookTable(String restaurantId, String date, int timeSlot, int numberOfSeats) {
        // check if the restaurant exists
        Restaurant restaurant = repository.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant with id " + restaurantId + " does not exist");
        }
        if (timeSlot < restaurant.getOpeningHour() || timeSlot > restaurant.getClosingHour()) {
            System.out.println("Time slot must be between " + restaurant.getOpeningHour() + " and " + restaurant.getClosingHour());
            return false;
        }
        // check if the date is within the allowed range
        // say we only take booking for date + 2 days (can be configured later)
        Date today = new Date();
        Date bookingDate = sdf.parse(date);
        if (bookingDate.before(today) || bookingDate.after(new Date(today.getTime() + 2 * 24 * 60 * 60 * 1000))) {
            System.out.println("Booking date must be within the next 2 days");
            return false;
        }

        // otherwise it is well within the range
        Map<Integer, Integer> slots = restaurant.getSlotAvailability().getOrDefault(date,
                generateSlots(
                        restaurant.getOpeningHour(),
                        restaurant.getClosingHour(),
                        restaurant.getSeatingCapacity())
        );

        // now check if the slot is available
        Integer availableSeats = slots.get(timeSlot);
        if (availableSeats < numberOfSeats) {
            System.out.println("Not enough seats available for the requested time slot");
            return false;
        }
        // if available, then update the slot availability
        slots.put(timeSlot, availableSeats - numberOfSeats);
        restaurant.getSlotAvailability().put(date, slots);
        // update the restaurant in the repository
        repository.updateRestaurant(restaurant);
        return true;
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
}

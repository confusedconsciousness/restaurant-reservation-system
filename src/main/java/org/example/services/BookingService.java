package org.example.services;

import lombok.SneakyThrows;
import org.example.enums.BookingStatus;
import org.example.models.Booking;
import org.example.models.Restaurant;
import org.example.repositories.BookingRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class BookingService {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private final RestaurantService restaurantService;
    private final BookingRepository bookingRepository;

    public BookingService (
            RestaurantService restaurantService,
            BookingRepository bookingRepository
    ) {
        this.restaurantService = restaurantService;
        this.bookingRepository = bookingRepository;
    }

    @SneakyThrows
    public Optional<Booking> bookTable (
            String userId,
            String restaurantId,
            String date,
            String time,
            int numberOfTables
    ) {
        // check if the restaurant exists
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant with id " + restaurantId + " does not exist");
        }
        // check if the date is within the allowed range
        // say we only take booking for date + 2 days (can be configured later)
        Date today = new Date();
        Date bookingDate = sdf.parse(date);
        if (bookingDate.before(today) || bookingDate.after(new Date(today.getTime() + 2 * 24 * 60 * 60 * 1000))) {
            System.out.println("Booking date must be within the next 2 days");
            return Optional.empty();
        }
        return createBooking(userId, restaurant, date, time, numberOfTables);
    }

    private synchronized Optional<Booking> createBooking (
            String userId,
            Restaurant restaurant,
            String date,
            String time,
            int numberOfTables
    ) {
        // book the slot
        if (restaurant.bookSlot(date, time, numberOfTables)) {
            // create a booking record
            Booking booking = new Booking(userId, restaurant.getId(), date, time, numberOfTables);
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            return Optional.of(bookingRepository.save(booking));
        } else {
            System.out.println("Not enough slots available for the requested date and time");
            return Optional.empty(); // or throw an exception
        }

    }
}

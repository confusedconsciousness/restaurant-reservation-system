package org.example.repositories;

import org.example.models.Booking;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BookingRepositoryImpl implements BookingRepository {
    private final AtomicLong counter = new AtomicLong(1);
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

    @Override
    public Booking save(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        String bookingId = generateBookingId();
        booking.setId(bookingId);
        bookings.put(bookingId, booking);
        return booking;
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.of(bookings.get(id));
    }

    private String generateBookingId() {
        return "B" + counter.getAndIncrement();
    }
}

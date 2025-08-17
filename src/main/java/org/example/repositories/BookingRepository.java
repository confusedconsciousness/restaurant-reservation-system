package org.example.repositories;

import org.example.models.Booking;

import java.util.Optional;

public interface BookingRepository {
    Booking save (Booking booking);

    Optional<Booking> findById (String id);
}

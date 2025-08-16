package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Cuisine;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private String id;
    private String name;
    private Set<Cuisine> cuisines;
    private double costOfTwo;

    private int seatingCapacity;

    // we can use the opening and closing hour to figure how many slots are available
    // for e.g. if i want to book a slot at 13:00 hour for 20 people but the only 10 seats are available,
// then we can return an error that the slot is not available
    private int openingHour;
    private int closingHour;

    // This map holds the availability of slots for each date.
    // we only take the booking for upto m days in the future
    // we can probably use a datastore here
    // for simplicity date will be 16-08-2025
    private Map<String, Map<Integer, Integer>> slotAvailability = new ConcurrentHashMap<>();

    private Address address;
    private String phoneNumber;
}

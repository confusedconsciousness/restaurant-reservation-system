package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.BookingStatus;

@Data
@NoArgsConstructor
public class Booking {
    private String id;
    private String userId;
    private String restaurantId;
    private String date;
    private String time;
    private int noOfPeople;
    private BookingStatus bookingStatus;

    public Booking(String userId, String restaurantId, String date, String time, int noOfTables) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
        this.time = time;
        this.noOfPeople = noOfTables;
        this.bookingStatus = BookingStatus.PENDING; // Default status
    }
}

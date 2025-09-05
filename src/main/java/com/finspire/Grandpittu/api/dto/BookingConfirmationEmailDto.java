package com.finspire.Grandpittu.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingConfirmationEmailDto {
    private String bookingId;
    private String roomType;
    private int guestCount;
    private LocalDateTime checkOutDateTime;

}

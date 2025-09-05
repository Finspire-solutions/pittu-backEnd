package com.finspire.Grandpittu.api.dto;

import com.finspire.Grandpittu.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailsResponseDto {
    private int id;
    private String bookedId;
    private String username;
    private String email;
    private String phoneNo;
    private String message;
    private int tableNo;
    private LocalDateTime dateAndTime;
    private BookingStatus status;
    private int guestNo;
}

package com.finspire.Grandpittu.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDto {

    private String username;
    private String email;
    private String phoneNo;
    private int tableNo;
    private LocalDateTime dateTime;
}
package com.finspire.Grandpittu.api.dto;

import com.finspire.Grandpittu.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RejectRequestDto {
    @NotNull(message = "Id can't be null")
    private int bookingId;
    private String message;
    @NotNull(message = "Id can't be null")
    private BookingStatus bookingStatus;
}

package com.finspire.Grandpittu.api.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class BookingRequestDto {

    @NotNull(message = "username can't be null")
    private String username;
    @NotNull(message = "username can't be null")
    @Email(message = "email should be format")
    private String email;
    @NotBlank(message = "phone number can't be blank")
    private String phoneNo;
    private String message;
    private int tableNo;
    @NotNull(message = "date can't be null")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateAndTime;
    private int guestNo;

}

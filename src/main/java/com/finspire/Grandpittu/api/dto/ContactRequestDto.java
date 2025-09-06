package com.finspire.Grandpittu.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDto {
    @NotNull(message = "can not be fullName null")
    private String fullname;
    @NotNull(message = "can not be Email null")
    @Email(message = "Email should be format")
    private String email;
    @NotNull(message = "can not be Email null")
    private String phoneNo;
    private String subject;
    @NotNull(message = "can not be Email null")
    private String message;
}

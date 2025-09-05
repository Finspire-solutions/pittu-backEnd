package com.finspire.Grandpittu.api.controller.user;

import com.finspire.Grandpittu.api.dto.BookingRequestDto;
import com.finspire.Grandpittu.api.dto.BookingResponseDto;
import com.finspire.Grandpittu.api.dto.RejectRequestDto;
import com.finspire.Grandpittu.api.dto.RejectedResponseDto;
import com.finspire.Grandpittu.service.user.ReserveTableDetails;
import com.finspire.Grandpittu.service.SmsService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Validated
public class BookingController {

    private final ReserveTableDetails bookingService;
    private final SmsService smsService;

    @PostMapping()
    public ResponseEntity<BookingResponseDto> saveBookingDetails(@RequestBody @Valid BookingRequestDto request) throws MessagingException, UnsupportedEncodingException {
        BookingResponseDto responseDto = bookingService.saveBookingDetails(request);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}


package com.finspire.Grandpittu.api.controller.user;

import com.finspire.Grandpittu.api.dto.BookingRequestDto;
import com.finspire.Grandpittu.api.dto.BookingResponseDto;
import com.finspire.Grandpittu.api.dto.RejectRequestDto;
import com.finspire.Grandpittu.api.dto.RejectedResponseDto;
import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import com.finspire.Grandpittu.repository.ReserveTableRepository;
import com.finspire.Grandpittu.service.user.ReserveTableDetails;
import com.finspire.Grandpittu.service.SmsService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Validated
@Slf4j
public class BookingController {
    @Value("${notifylk.userId}")
    private String userId;

    @Value("${notifylk.apiKey}")
    private String apiKey;

    @Value("${notifylk.senderId}")
    private String senderId;
    private final ReserveTableRepository reserveTableRepository;
    private final RestTemplate restTemplate;
    private final ReserveTableDetails bookingService;
    private final SmsService smsService;
    private final ReserveTableRepository repository;

    @PostMapping()
    public ResponseEntity<BookingResponseDto> saveBookingDetails(@RequestBody @Valid BookingRequestDto request) throws MessagingException, UnsupportedEncodingException {
        BookingResponseDto responseDto = bookingService.saveBookingDetails(request);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}


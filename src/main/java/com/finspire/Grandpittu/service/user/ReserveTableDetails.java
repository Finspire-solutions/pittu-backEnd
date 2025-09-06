package com.finspire.Grandpittu.service.user;

import com.finspire.Grandpittu.api.dto.BookingRequestDto;
import com.finspire.Grandpittu.api.dto.BookingResponseDto;
import com.finspire.Grandpittu.api.dto.RejectRequestDto;
import com.finspire.Grandpittu.api.dto.RejectedResponseDto;
import com.finspire.Grandpittu.constants.ApplicationConstants;
import com.finspire.Grandpittu.converter.ReserveTableConverter;
import com.finspire.Grandpittu.email.EmailService;
import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import com.finspire.Grandpittu.exception.ServiceException;
import com.finspire.Grandpittu.repository.ReserveTableRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import static com.finspire.Grandpittu.email.EmailTemplateName.BOOKING_CONFIRM;

@Service
@RequiredArgsConstructor
public class ReserveTableDetails {
    private final ReserveTableRepository reserveTableRepository;
    private final ReserveTableConverter reserveTableConverter;
    public BookingResponseDto saveBookingDetails(BookingRequestDto request){
        ReserveTable response = reserveTableRepository.save(reserveTableConverter.converter(request));
        return BookingResponseDto.builder()
                .phoneNo(response.getPhoneNo())
                .date(response.getDate())
                .time(response.getTime())
                .email(response.getEmail())
                .tableNo(response.getTableNo())
                .username(response.getUsername())
                .build();

    }

}
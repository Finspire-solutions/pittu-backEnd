package com.finspire.Grandpittu.converter;

import com.finspire.Grandpittu.api.dto.BookingRequestDto;
import com.finspire.Grandpittu.constants.ApplicationConstants;
import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import com.finspire.Grandpittu.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.finspire.Grandpittu.enums.BookingStatus.PENDING;

@Component
public class ReserveTableConverter {

    public ReserveTable converter(BookingRequestDto request){

        String email = request.getEmail();
        LocalTime time = request.getDateAndTime().toLocalTime();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HHmm"));
        String subName = email.split("@")[0];

//generate booking id
        String bookingId = subName+"_"+formattedTime;

        return ReserveTable.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNo(request.getPhoneNo())
                .tableNo(request.getTableNo())
                .dateAndTime(request.getDateAndTime())
                .message(request.getMessage())
                .bookedId(bookingId)
                .status(PENDING)
                .guestNo(request.getGuestNo())
                .build();
    }

}

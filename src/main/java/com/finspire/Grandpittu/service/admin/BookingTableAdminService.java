package com.finspire.Grandpittu.service.admin;

import com.finspire.Grandpittu.api.dto.AcceptRessponseDto;
import com.finspire.Grandpittu.api.dto.RejectRequestDto;
import com.finspire.Grandpittu.constants.ApplicationConstants;
import com.finspire.Grandpittu.email.EmailContentDto;
import com.finspire.Grandpittu.email.EmailService;
import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import com.finspire.Grandpittu.exception.ServiceException;
import com.finspire.Grandpittu.repository.ReserveTableRepository;
import com.finspire.Grandpittu.service.SmsService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;


import static com.finspire.Grandpittu.email.EmailTemplateName.ACCEPTED_EMAIL;
import static com.finspire.Grandpittu.email.EmailTemplateName.REJECT_EMAIL;

@Service
@RequiredArgsConstructor
public class BookingTableAdminService {
    private final ReserveTableRepository repository;
    private final EmailService emailService;
    private final SmsService smsService;
    public AcceptRessponseDto manageBookingTable(RejectRequestDto rejectRequest) throws MessagingException {
        ReserveTable existingReserveTable = repository.findById(rejectRequest.getBookingId())
                .orElseThrow(() -> new ServiceException(
                        ApplicationConstants.BOOKING_NOT_FOUND,
                        ApplicationConstants.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST
                ));

        BookingStatus newStatus;
        try {
            newStatus = BookingStatus.valueOf(rejectRequest.getBookingStatus().toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Invalid status: " + rejectRequest.getBookingStatus(),
                    ApplicationConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }

        existingReserveTable.setStatus(newStatus);
        repository.save(existingReserveTable);

        // Sending emails
        switch (newStatus) {
            case ACCEPTED -> {
                emailService.sendBookingRelatedEmail(
                        existingReserveTable,
                        EmailContentDto.builder()
                                .reason("Good news! Your reservation request has been accepted.")
                                .status("Accepted")
                                .subject("Booking Accepted")
                                .build(),
                        ACCEPTED_EMAIL
                );
                smsService.sendBookingConfirmSms(existingReserveTable);
            }
            case REJECTED -> {
                emailService.sendBookingRelatedEmail(
                        existingReserveTable,
                        EmailContentDto.builder()
                                .reason("We regret to inform you that your reservation request has been rejected.")
                                .status("Rejected")
                                .subject("Booking Rejected")
                                .build(),
                        REJECT_EMAIL
                );
                smsService.sendBookingRejectedSms(existingReserveTable);
            }
            case CANCELED -> emailService.sendBookingRelatedEmail(
                    existingReserveTable,
                    EmailContentDto.builder()
                            .reason("Your reservation has been canceled as per your request.")
                            .status("Canceled")
                            .subject("Booking Canceled")
                            .build(),
                    REJECT_EMAIL
            );

            case INCOMPLETED -> emailService.sendBookingRelatedEmail(
                    existingReserveTable,
                    EmailContentDto.builder()
                            .reason("Your reservation could not be completed due to missing information.")
                            .status("Incompleted")
                            .subject("Booking Incompleted")
                            .build(),
                    REJECT_EMAIL
            );

            case COMPLETED -> emailService.sendBookingRelatedEmail(
                    existingReserveTable,
                    EmailContentDto.builder()
                            .reason("Your reservation has been successfully completed.")
                            .status("Completed")
                            .subject("Booking Completed")
                            .build(),
                    REJECT_EMAIL
            );

            default -> throw new IllegalStateException("Unexpected status: " + newStatus);
        }


        String message = "Booking status updated to " + newStatus;

        return AcceptRessponseDto.builder()
                .message(message)
                .build();
    }

    public Page<ReserveTable> getAllAcceptedBookingDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateAndTime").descending());
        return repository.findByStatus(BookingStatus.ACCEPTED, pageable);
    }
    public Page<ReserveTable> getAllPendingBookingDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateAndTime").descending());
        return repository.findByStatus(BookingStatus.PENDING, pageable);
    }
    public Page<ReserveTable> getAllOtherStatusBookingDetails(int page, int size,String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateAndTime").descending());
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            return repository.findByStatus(bookingStatus, pageable);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(
                    ApplicationConstants.STATUS_NOT_FOUND,
                    ApplicationConstants.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

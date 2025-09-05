package com.finspire.Grandpittu.api.controller.admin;

import com.finspire.Grandpittu.api.dto.AcceptRessponseDto;
import com.finspire.Grandpittu.api.dto.RejectRequestDto;
import com.finspire.Grandpittu.entity.MenuItem;
import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import com.finspire.Grandpittu.repository.MenuRepository;
import com.finspire.Grandpittu.repository.ReserveTableRepository;
import com.finspire.Grandpittu.service.admin.BookingTableAdminService;
import com.finspire.Grandpittu.service.user.ReserveTableDetails;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBookingController {

    private final ReserveTableDetails reserveTableDetailsService;
    private final BookingTableAdminService adminService;
    private final ReserveTableRepository repository;
    private final MenuRepository menuRepository;
    @PostMapping("/manage-booking")
    public ResponseEntity<AcceptRessponseDto> manageTableBooking(@RequestBody RejectRequestDto rejectRequest) throws MessagingException {
        AcceptRessponseDto response = adminService.manageBookingTable(rejectRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAllBookingStatusAccepted")
    public Page<ReserveTable> getAllAcceptedBookingDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return adminService.getAllAcceptedBookingDetails(page, size);
    }
    @GetMapping("/getAllBookingStatusPending")
    public Page<ReserveTable> getAllPendingBookingDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return adminService.getAllPendingBookingDetails(page, size);
    }
    @GetMapping("/getAllBookingOtherStatus")
    public Page<ReserveTable> getAllOtherStatusBookingDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "10") String status

    ){
        return adminService.getAllOtherStatusBookingDetails(page, size,status);
    }

//    @PostConstruct
//    public void initData() {
//        for (int i = 0;i<50;i++){
//
//            MenuItem demo = new MenuItem();
//            Random num = new Random();
//            demo.setName("John Doe"+num.nextInt(50));
//            demo.setDescription("cchayanthikan"+num.nextInt(50)+"@gmail.com");
//            demo.setPrice(BigDecimal.valueOf(100.00));
//            demo.setCategory("I'd like a window seat, please.");
//            demo.setImageUrl("image"+i+".png");
//            demo.setAvailable(true);
//            menuRepository.save(demo);
//        }
//    }


}

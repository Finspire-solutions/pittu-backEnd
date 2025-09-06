package com.finspire.Grandpittu.service.schedule;

import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.entity.SmsLog;
import com.finspire.Grandpittu.enums.BookingStatus;
import com.finspire.Grandpittu.repository.ReserveTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasksService {
    @Value("${notifylk.userId}")
    private String userId;

    @Value("${notifylk.apiKey}")
    private String apiKey;

    @Value("${notifylk.senderId}")
    private String senderId;
    private final ReserveTableRepository reserveTableRepository;
    private final RestTemplate restTemplate;
    // Initial Delay - Wait 10 seconds before first execution, then every 5 seconds
    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    public void scheduleTaskWithInitialDelay() {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalTime reminderTime = now.plusMinutes(2);
        String formattedTime = reminderTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println("Checking bookings for " + today + " at " + formattedTime);

        List<ReserveTable> reserveTables = reserveTableRepository
                .findAllByDateAndTimeAndStatus(today, formattedTime, BookingStatus.ACCEPTED);
        log.info("------>{}",reserveTables);
        if (reserveTables != null && !reserveTables.isEmpty()) {
            for (ReserveTable booking : reserveTables) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                    String message = "Hello "+booking.getUsername()+",\n" +
                            "This is a friendly reminder that your table reservation at GRANDPITTU \n" +
                            "is scheduled for "+booking.getDate()+" at "+booking.getTime()+".\n" +
                            "Table Number: "+booking.getTableNo()+"\n" +
                            "We look forward to serving you!  \n" +
                            "If you need to make any changes, please contact us in advance.\n" +
                            "Thank you,  \n" +
                            "GRANDPITTU Team\n";

                    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                    params.add("user_id", userId);
                    params.add("api_key", apiKey);
                    params.add("sender_id", senderId);
                    params.add("to", booking.getPhoneNo());
                    params.add("message", message);

                    String url = "https://app.notify.lk/api/v1/send";

                    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
                    restTemplate.postForObject(url, request, String.class);
            }
        }
    }

}

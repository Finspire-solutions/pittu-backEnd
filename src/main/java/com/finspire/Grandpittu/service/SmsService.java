package com.finspire.Grandpittu.service;

import com.finspire.Grandpittu.api.dto.BookingResponseDto;
import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.entity.SmsLog;
import com.finspire.Grandpittu.repository.SmsLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Slf4j
public class SmsService {

    private final RestTemplate restTemplate;
    private final SmsLogRepository smsLogRepository;

    @Value("${notifylk.userId}")
    private String userId;

    @Value("${notifylk.apiKey}")
    private String apiKey;

    @Value("${notifylk.senderId}")
    private String senderId;

    public SmsService(RestTemplate restTemplate, SmsLogRepository smsLogRepository) {
        this.restTemplate = restTemplate;
        this.smsLogRepository = smsLogRepository;
    }

    public String sendBookingConfirmSms(ReserveTable reserveTable) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String message = "Dear " + reserveTable.getUsername() + ",\n" +
                    "Your reservation at Grand Pittu has been confirmed!\n" +
                    "We look forward to welcoming you.\n\n" +
                    "Table No: " + reserveTable.getTableNo() + "\n" +
                    "Date: " + reserveTable.getDateAndTime() + "\n" +    // use actual date field
//                    "⏰ Time: " + reserveTable.getTime() + "\n" +    // use actual time field
                    "Location: https://maps.app.goo.gl/XSBMC4iWn5Z3opyA6\n\n" +
                    "Call us for help: 0762565485\n" +
                    "- GRANDPITTU";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("user_id", userId);
            params.add("api_key", apiKey);
            params.add("sender_id", senderId);
            params.add("to", reserveTable.getPhoneNo());
            params.add("message", message);

            String url = "https://app.notify.lk/api/v1/send";

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(url, request, String.class);

            // Save log to DB - store the original message, not encoded one
            SmsLog log = SmsLog.builder()
                    .mobile(reserveTable.getPhoneNo())
                    .message(message)  // Store original message
                    .response(response)
                    .sentAt(LocalDateTime.now())
                    .build();
            smsLogRepository.save(log);

            return response;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String sendBookingRejectedSms(ReserveTable reserveTable) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String message = "Dear " + reserveTable.getUsername() + ",\n" +
                    "We regret to inform you that your reservation request at Grand Pittu cannot be confirmed at this time, " +
                    "as no tables are available for your selected date and time.\n\n" +
                    "📅 Date: " + reserveTable.getDateAndTime() + "\n" +
//                    "⏰ Time: " + reserveTable.getTime() + "\n\n" +
                    "🙏 We apologize for the inconvenience. " +
                    "Please consider choosing another date or time.\n\n" +
                    "📞 For assistance, call us at 0762565485\n" +
                    "- Grand Pittu";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("user_id", userId);
            params.add("api_key", apiKey);
            params.add("sender_id", senderId);
            params.add("to", reserveTable.getPhoneNo());
            params.add("message", message);

            String url = "https://app.notify.lk/api/v1/send";

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(url, request, String.class);

            SmsLog log = SmsLog.builder()
                    .mobile(reserveTable.getPhoneNo())
                    .message(message)  // Store original message
                    .response(response)
                    .sentAt(LocalDateTime.now())
                    .build();
            smsLogRepository.save(log);

            return response;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    public String sendBookingCancelledSms(ReserveTable reserveTable) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String message = "Dear " + reserveTable.getUsername() + ",\n" +
                    "Your reservation at Grand Pittu has been successfully cancelled as per your request.\n\n" +
                    "📅 Date: " + reserveTable.getDateAndTime() + "\n" +
//                    "⏰ Time: " + reserveTable.getTime() + "\n\n" +
                    "🙏 We hope to serve you in the future.\n" +
                    "📞 For assistance, call us at 0762565485\n" +
                    "- Grand Pittu";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("user_id", userId);
            params.add("api_key", apiKey);
            params.add("sender_id", senderId);
            params.add("to", reserveTable.getPhoneNo());
            params.add("message", message);

            String url = "https://app.notify.lk/api/v1/send";

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(url, request, String.class);

            SmsLog log = SmsLog.builder()
                    .mobile(reserveTable.getPhoneNo())
                    .message(message)  // Store original message
                    .response(response)
                    .sentAt(LocalDateTime.now())
                    .build();
            smsLogRepository.save(log);

            return response;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    public String sendBookingSuccessfullyCompletedSms(ReserveTable reserveTable) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String message = "Dear " + reserveTable.getUsername() + ",\n" +
                    "Thank you for dining with us at Grand Pittu! We are delighted that your reservation was successfully completed.\n\n" +
                    "🍽️ We hope you enjoyed your time with us.\n" +
                    "⭐ Your feedback means a lot — feel free to share your experience.\n\n" +
                    "📞 For future bookings, call 0762565485\n" +
                    "- Grand Pittu";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("user_id", userId);
            params.add("api_key", apiKey);
            params.add("sender_id", senderId);
            params.add("to", reserveTable.getPhoneNo());
            params.add("message", message);

            String url = "https://app.notify.lk/api/v1/send";

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            String response = restTemplate.postForObject(url, request, String.class);

            SmsLog log = SmsLog.builder()
                    .mobile(reserveTable.getPhoneNo())
                    .message(message)  // Store original message
                    .response(response)
                    .sentAt(LocalDateTime.now())
                    .build();
            smsLogRepository.save(log);

            return response;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}


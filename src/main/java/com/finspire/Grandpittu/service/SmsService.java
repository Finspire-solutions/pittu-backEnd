package com.finspire.Grandpittu.service;

import com.finspire.Grandpittu.api.dto.BookingResponseDto;
import com.finspire.Grandpittu.entity.SmsLog;
import com.finspire.Grandpittu.repository.SmsLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
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

    public String sendSms(BookingResponseDto responseDto) {
        try {
            String message = "Dear "+responseDto.getUsername()+", your reservation at Grand Pittu has been confirmed!\n" +
                    "We look forward to welcoming you.\n\n" +
                    "📋 Table No:" +responseDto.getTableNo()+"\n"+
                    "📅 Date:" +responseDto.getTableNo()+"\n"+
                    "⏰ Time:"+responseDto.getTableNo()+"\n"+
                    "📍 Location: https://maps.app.goo.gl/XSBMC4iWn5Z3opyA6\n\n" +
                    "📞 Call us for help: 0762565485\n" +
                    "- Grand Pittu";

            // URL encode only for the API request
            String encodedMsg = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

            String url = String.format(
                    "https://app.notify.lk/api/v1/send?user_id=%s&api_key=%s&sender_id=%s&to=%s&message=%s",
                    userId, apiKey, senderId, responseDto.getPhoneNo(), encodedMsg
            );

            String response = restTemplate.getForObject(url, String.class);

            // Save log to DB - store the original message, not encoded one
            SmsLog log = SmsLog.builder()
                    .mobile(responseDto.getPhoneNo())
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


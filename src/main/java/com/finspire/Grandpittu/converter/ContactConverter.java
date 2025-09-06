package com.finspire.Grandpittu.converter;

import com.finspire.Grandpittu.api.dto.ContactRequestDto;
import com.finspire.Grandpittu.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter {
    public Contact ContactConverter(ContactRequestDto requestDto){
        return Contact.builder()
                .fullname(requestDto.getFullname())
                .email(requestDto.getEmail())
                .phoneNo(requestDto.getPhoneNo())
                .subject(requestDto.getSubject())
                .message(requestDto.getMessage())
                .build();
    }
}

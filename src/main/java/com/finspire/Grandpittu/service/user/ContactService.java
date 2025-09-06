package com.finspire.Grandpittu.service.user;

import com.finspire.Grandpittu.api.dto.ContactRequestDto;
import com.finspire.Grandpittu.constants.ApplicationConstants;
import com.finspire.Grandpittu.converter.ContactConverter;
import com.finspire.Grandpittu.entity.Contact;
import com.finspire.Grandpittu.exception.ServiceException;
import com.finspire.Grandpittu.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactConverter contactConverter;

    public ResponseEntity<String> saveContactDetails(ContactRequestDto requestDto) {
        contactRepository.save(contactConverter.ContactConverter(requestDto));
        return ResponseEntity.ok("Successfully Saved Contact Details");
    }


    public ResponseEntity<String> deleteContactDetails(Long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null){
            contactRepository.deleteById(id);
            return ResponseEntity.ok("Successfully Deleted");
        }else{
             throw new ServiceException(ApplicationConstants.CONTACT_DETAIL_NOT_FOUND,ApplicationConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Contact> getContactDetailsById(Long id) {
        return contactRepository.findById(id);
    }

    public Page<Contact> getAllContactDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return contactRepository.findAll(pageable);
    }
}

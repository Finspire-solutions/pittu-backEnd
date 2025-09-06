package com.finspire.Grandpittu.api.controller.user;

import com.finspire.Grandpittu.api.dto.ContactRequestDto;
import com.finspire.Grandpittu.entity.Contact;
import com.finspire.Grandpittu.service.user.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;
    @PostMapping()
    public ResponseEntity<String> saveContactDetails(@RequestBody @Valid ContactRequestDto requestDto){
        return contactService.saveContactDetails(requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") Long id){
        return contactService.deleteContactDetails(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactDetailById(@PathVariable("id") Long id){
        return contactService.getContactDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/getAll")
    public Page<Contact> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return contactService.getAllContactDetails(page,size);
    }
}

package com.finspire.Grandpittu.repository;

import com.finspire.Grandpittu.entity.Contact;
import com.finspire.Grandpittu.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    Page findAll(Pageable pageable);
}

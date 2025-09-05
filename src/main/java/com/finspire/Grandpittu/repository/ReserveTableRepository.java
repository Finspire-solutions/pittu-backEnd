package com.finspire.Grandpittu.repository;

import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveTableRepository extends JpaRepository<ReserveTable,Integer> {

    Page findByStatus(BookingStatus bookingStatus, Pageable pageable);
}

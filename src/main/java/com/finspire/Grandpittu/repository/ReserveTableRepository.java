package com.finspire.Grandpittu.repository;

import com.finspire.Grandpittu.entity.ReserveTable;
import com.finspire.Grandpittu.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReserveTableRepository extends JpaRepository<ReserveTable,Integer> {

    Page findByStatus(BookingStatus bookingStatus, Pageable pageable);

//    @Query(value = "SELECT * FROM reserve_table WHERE date = :date AND time = :time AND status = :status", nativeQuery = true)
    List<ReserveTable> findAllByDateAndTimeAndStatus(LocalDate date, String time, BookingStatus status);
}

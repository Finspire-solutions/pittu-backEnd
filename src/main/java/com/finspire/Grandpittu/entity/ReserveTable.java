package com.finspire.Grandpittu.entity;

import com.finspire.Grandpittu.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reserve_table")
@Entity
public class ReserveTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "booked_id")
    private String bookedId;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "message")
    private String message;
    @Column(name = "table_no")
    private int tableNo;
    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;  //Accepted,Rejected,Pending,Complete
    @Column(name = "guest_no")
    private int guestNo;
}

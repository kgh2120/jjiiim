package com.kk.jjiiim.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation extends BaseEntity{

    private String customerInfo;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;



    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation")
    private Review review;

}

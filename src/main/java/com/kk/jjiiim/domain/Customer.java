package com.kk.jjiiim.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@DiscriminatorValue(value = "customer")
@Entity
public class Customer extends User{

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();

}

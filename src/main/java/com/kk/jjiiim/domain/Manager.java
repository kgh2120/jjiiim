package com.kk.jjiiim.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@DiscriminatorValue(value = "manager")
@Entity
public class Manager extends User{

    @OneToMany(mappedBy = "manager")
    List<Store> stores = new ArrayList<>();
}

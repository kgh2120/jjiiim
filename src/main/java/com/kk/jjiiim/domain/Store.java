package com.kk.jjiiim.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Store extends BaseEntity{

    private String name;
    private String latitude; // TODO db에서 계산 안될 경우엔 double로 변경해야 함.
    private String longitude;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id")
    private Manager manager;
}

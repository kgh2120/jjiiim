package com.kk.jjiiim.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Store extends BaseEntity{

    @Column(unique = true, updatable = false, nullable = false)
    private String name;
    @Column(nullable = false, updatable = false)
    private double latitude;
    @Column(nullable = false, updatable = false)
    private double longitude;
    @Column(nullable = false, updatable = false)
    private String address;
    @Column(nullable = false, updatable = false)
    private String description;
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public void associatedWithManager(Manager manager){
        this.manager = manager;
        manager.associatedWithStore(this);
    }
}

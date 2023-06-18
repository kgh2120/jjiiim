package com.kk.jjiiim.domain;


import jakarta.persistence.*;

@DiscriminatorColumn(name="user_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "users")
public abstract class User extends BaseEntity{

    private String loginId;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}

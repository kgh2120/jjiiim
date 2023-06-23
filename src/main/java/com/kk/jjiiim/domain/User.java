package com.kk.jjiiim.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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

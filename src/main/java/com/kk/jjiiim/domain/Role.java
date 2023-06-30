package com.kk.jjiiim.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_CUSTOMER("CUSTOMER"), ROLE_MANAGER("MANAGER")

    ;

    private final String roleName;
}

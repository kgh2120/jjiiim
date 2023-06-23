package com.kk.jjiiim.util;

import com.kk.jjiiim.domain.Customer;
import com.kk.jjiiim.domain.Manager;
import com.kk.jjiiim.domain.Role;
import com.kk.jjiiim.domain.User;
import com.kk.jjiiim.dto.SignUp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserCreateFactory {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Customer createCustomer(SignUp.Request userInfo) {
        return Customer.builder()
                .loginId(userInfo.getLoginId())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .phoneNumber(userInfo.getPhoneNumber())
                .role(Role.CUSTOMER)
                .build();
    }
}

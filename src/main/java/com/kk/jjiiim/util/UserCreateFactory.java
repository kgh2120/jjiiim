package com.kk.jjiiim.util;

import com.kk.jjiiim.domain.Customer;
import com.kk.jjiiim.domain.Manager;
import com.kk.jjiiim.domain.Role;
import com.kk.jjiiim.dto.SignUp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateFactory {
    public static Customer createCustomer(SignUp.Request userInfo, PasswordEncoder passwordEncoder) {
        return Customer.builder()
                .loginId(userInfo.getLoginId())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .phoneNumber(userInfo.getPhoneNumber())
                .role(Role.CUSTOMER)
                .build();
    }

    public static Manager createManager(SignUp.Request userInfo,PasswordEncoder passwordEncoder) {
        return Manager.builder()
                .loginId(userInfo.getLoginId())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .phoneNumber(userInfo.getPhoneNumber())
                .role(Role.MANAGER)
                .build();
    }
}

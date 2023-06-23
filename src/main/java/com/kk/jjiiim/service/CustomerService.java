package com.kk.jjiiim.service;

import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.repository.CustomerRepository;
import com.kk.jjiiim.repository.UserRepository;
import com.kk.jjiiim.util.SignUpValidator;
import com.kk.jjiiim.util.UserCreateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    public void signUp(SignUp.Request dto) {
        SignUpValidator.validateSignUp(userRepository,dto);
        customerRepository.save(UserCreateFactory.createCustomer(dto));
    }
}

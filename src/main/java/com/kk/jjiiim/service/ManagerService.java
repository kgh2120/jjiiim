package com.kk.jjiiim.service;


import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.repository.ManagerRepository;
import com.kk.jjiiim.repository.UserRepository;
import com.kk.jjiiim.util.SignUpValidator;
import com.kk.jjiiim.util.UserCreateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUp.Request dto) {
        SignUpValidator.validateSignUp(userRepository,dto);
        managerRepository.save(UserCreateFactory.createManager(dto,passwordEncoder));
    }
}

package com.kk.jjiiim.controller;

import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<Void> customerSignUp(@RequestBody @Valid SignUp.Request dto){

        customerService.signUp(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }


}

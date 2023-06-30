package com.kk.jjiiim.controller;


import com.kk.jjiiim.dto.MyStores;
import com.kk.jjiiim.dto.RegisterStore;
import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/signup")
    public ResponseEntity<Void> customerSignUp(@RequestBody @Valid SignUp.Request dto){
        managerService.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/store")
    public ResponseEntity<Void> registerStore(@RequestBody @Valid RegisterStore.Request dto){
        managerService.registerStore(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("/store")
    public ResponseEntity<List<MyStores>> readMyStores(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(managerService.readMyStores());
    }
}

package com.kk.jjiiim.controller;


import com.kk.jjiiim.dto.CheckId;
import com.kk.jjiiim.dto.RequestCode;
import com.kk.jjiiim.service.CommonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/common")
@RestController
public class CommonController {

    private final CommonService commonService;


    @PostMapping("/id")
    public ResponseEntity<CheckId.Response> checkDuplicateId(@RequestBody @Valid  CheckId.Request dto){
        return ResponseEntity.ok(commonService.isDuplicatedId(dto));
    }

}

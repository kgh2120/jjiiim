package com.kk.jjiiim.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RequestCode {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        @NotBlank
        private String phoneNumber;
    }
}

package com.kk.jjiiim.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CheckPhoneNumber {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @NotBlank
        private String phoneNumber;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private boolean result;
    }
}

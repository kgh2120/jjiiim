package com.kk.jjiiim.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SignUp {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotBlank
        private String loginId;
        @NotBlank
        private String password;
        @NotBlank
        private String passwordRepeat;
        @NotBlank
        private String phoneNumber;
        @NotBlank
        private String name;
    }
}

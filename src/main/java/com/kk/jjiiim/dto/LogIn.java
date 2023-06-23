package com.kk.jjiiim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LogIn {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String loginId;
        private String password;
    }
}

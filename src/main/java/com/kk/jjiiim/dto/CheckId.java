package com.kk.jjiiim.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CheckId {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @NotBlank
        private String loginId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private boolean result;
    }
}

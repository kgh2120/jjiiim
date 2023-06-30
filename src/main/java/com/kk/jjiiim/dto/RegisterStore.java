package com.kk.jjiiim.dto;

import com.kk.jjiiim.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterStore {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotBlank
        private String name;
        @NotBlank
        private String address;
        @NotNull
        private double latitude;
        @NotNull
        private double longitude;
        @NotBlank
        private String description;
        @NotNull
        private Category category;
    }
}

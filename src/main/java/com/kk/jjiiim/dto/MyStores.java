package com.kk.jjiiim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyStores {


        private Long id;
        private String name;
        private double rating;
        private long nOfReviews;

}

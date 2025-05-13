package com.example.demo.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private String name;
    private String location;
    private String rating;
    private String responseRate;
    private String yearsOnAlibaba;
    private String profileUrl;
    private String productTitle;
    private String price;
    private String minOrder;
    private String imageUrl;
} 
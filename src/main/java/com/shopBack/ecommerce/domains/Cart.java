package com.shopBack.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private String product_name;
    private String product_code;
    private int quantity;
    private String price;
    private String total;
    private String image;
    private String description;
    private String note1;
    private String note2;
    private String note3;
    private String note4;
    private String note5;
    private Forfait forfait;
}
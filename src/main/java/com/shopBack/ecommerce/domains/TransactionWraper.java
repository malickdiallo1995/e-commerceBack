package com.shopBack.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionWraper {
    private String application_id;
    private String date;
    private String selected_payment_way;
    private String amount;
    private String currency;
    private String payment_option;
    private String order_ref;
    private String items;
    private List<Cart> cart;
}

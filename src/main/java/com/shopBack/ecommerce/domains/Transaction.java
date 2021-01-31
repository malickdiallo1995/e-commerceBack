package com.shopBack.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity  {
    private String currency;
    private String payment_options;
    private String order_ref;
    private int items;
    private String product_name;
    private String product_code;
    private String quantity;
    @NotNull(message = "Le montant de la transaction est obligatoire")
    private Double price;
    @NotNull(message = "Le montant total de transaction est obligatoire")
    private Double total;

    private String transaction_id;
    private  String url;
    private  String application_id;
    private  String status;
    @NotNull(message = "L'id de la commande est onligatoire")
    private int idCommande;
}

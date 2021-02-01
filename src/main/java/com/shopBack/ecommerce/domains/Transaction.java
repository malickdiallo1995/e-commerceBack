package com.shopBack.ecommerce.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity  {
    private String currency;
    private String payment_options;
    private String order_ref;
    private String items;
    @NotNull(message = "Le montant total de transaction est obligatoire")
    private String amount;
    private String transaction_id;
    private  String url;
    private  String application_id;
    private  String status;
    @NotNull(message = "La date de la commande est onligatoire")
    private String date;
    @NotNull(message = "Le status de la commande est onligatoire")
    private String status_commande;

}
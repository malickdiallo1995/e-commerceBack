package com.shopBack.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Commande extends BaseEntity{
    @NotNull(message = "Le date de commande est obligatoire")
    private Double prix_total;
    @NotNull(message = "Le date de commande est obligatoire")
    private String dateCommande;
    @NotNull
    private String statutCommande;
}

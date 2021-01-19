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
public class QuittanceCommande extends BaseEntity{

    @NotNull(message = "La date de creation du quittance est obligatoire")
    private Timestamp date;

    @NotNull(message = "Le mois du quittance est obligatoire")
    private Integer mois;

    @NotNull(message = "L'annee du quittance est obligatoire")
    private Integer annee;

    @NotNull(message = "L'etat du quittance est obligatoire")
    private String etat;

    @NotNull(message = "Le montant à payer est obligatoire")
    private Double montant;

    @NotNull(message = "Le montant du reliquat est obligatoire")
    private Double reliquat;


    private int idmodeReglement;


    private int idLigneCommande;

}

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
public class LigneCommande extends BaseEntity{

    @NotNull(message = "Le prix du telephone est obligatoire")
    private Double prixTelephone;

    @NotNull(message = "Le montant du forfait est obligatoire")
    private Double montantForfait;

    @NotNull(message = "Le total est obligatoire")
    private Double total;

    @NotNull(message = "Le type de contrat est obligatoire")
    private String engagement;

    @NotNull(message = "Le nombre de mois engagement est obligatoire")
    private Integer nbMoisEngagement;

    @NotNull(message = "Le date de commande est obligatoire")
    private Timestamp date;


    private int idmodeReglement;


    private int clientIdClient;


    private int idForfait;


    private int idOffre;


}

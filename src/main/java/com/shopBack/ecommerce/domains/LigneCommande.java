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
    private String prixTelephone;

    @NotNull(message = "Le montant du forfait est obligatoire")
    private String montantForfait;

    @NotNull(message = "Le total est obligatoire")
    private String total;

    @NotNull(message = "Le type de contrat est obligatoire")
    private String engagement;

    @NotNull(message = "Le nombre de mois engagement est obligatoire")
    private Integer nbMoisEngagement;

    private int idmodeReglement;

    private int clientIdClient;

    private int idForfait;

    @NotNull(message = "l'id de la transaction est obligatoire")
    private int idTransaction;

    @NotNull(message = "la commande contient-elle un forfait ? Ce champs est obligatoire")
    private boolean containforfait;

    @NotNull(message = "la quantité est obligatoire")
    private int quantity;

    @NotNull(message = "le product code est obligatoire")
    private String product_code;

    @NotNull(message = "le product name est obligatoire")
    private String product_name;

}

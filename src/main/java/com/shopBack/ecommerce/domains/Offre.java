package com.shopBack.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offre extends BaseEntity {

    private Double prixNeuf;
    private Double prixReconditionné;

    @NotNull(message = "Le nom de l'offre est obligatoire")
    private String nom;

    private int idTelephone;

}

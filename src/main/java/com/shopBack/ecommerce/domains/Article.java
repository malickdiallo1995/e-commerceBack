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

public class Article extends BaseEntity{

    @NotNull(message = "Le numero EMEI est obligatoire")
    private String imei;

    @NotNull(message = "Le numero de serie est obligatoire")
    private String numeroSerie;
    @NotNull(message = "La couleur est obligatoire")
    private String couleur;
    @NotNull(message = "L'etat est obligatoire")
    private String etat;

    private int telephoneIdTelephone;


}

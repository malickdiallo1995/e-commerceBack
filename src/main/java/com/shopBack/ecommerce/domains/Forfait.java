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

public class Forfait extends BaseEntity{
    @NotNull(message = "Le type de forfait est obligatoire")
    private String type;
    @NotNull(message = "Le nom du forfait est obligatoire")
    private String nom;
    @NotNull(message = "La description du forfait est obligatoire")
    private String description;
    @NotNull(message = "Le type post pre paid est obligatoire")
    private String typePrePostPaid;
    @NotNull(message = "Le mprix du forfait est obligatoire")
    private Double montant;
    private int idOperateur;
    @NotNull(message = "La validite est obligatoire")
    private String validite;
    @NotNull(message = "L'information voice est obligatoire")
    private String voice;
    @NotNull(message = "L'information sms est obligatoire")
    private String sms;
    @NotNull(message = "L'information est obligatoire")
    private String data;

    private String codeActivation;
    private String urlImage;


}

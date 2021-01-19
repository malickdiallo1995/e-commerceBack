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
public class Utilisateur extends BaseEntity{

    @NotNull(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "Le prenom est obligatoire")
    private String prenom;

    @NotNull(message = "L'email est  obligatoire")
    private String email;

    @NotNull(message = "Le login est obligatoire")
    private String login;

    @NotNull(message = "Le mot de passe est obligatoire")
    private String password;


    private int idClient;

    @NotNull(message = "Le profil est obligatoire")
    private String profil;


}

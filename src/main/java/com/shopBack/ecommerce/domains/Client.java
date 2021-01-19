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

public class Client extends BaseEntity{

    @NotNull(message = "Le nom du client est obligatoire")
    private String nom;
    @NotNull(message = "Le prenom du client est obligatoire")
    private String prenom;
    @NotNull(message = "L'adresse du client est obligatoire")
    private String adresse;
    @NotNull(message = "Le numero de téléphone du client est obligatoire")
    private String telephone;
    @NotNull(message = "L'email du client est obligatoire")
    private String email;


}

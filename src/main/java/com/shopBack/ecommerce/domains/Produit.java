package com.shopBack.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produit extends BaseEntity{

    @NotNull(message = "Le nom est obligatoire")
    private String name;

    @NotNull(message = "La marque est obligatoire")
    private int idMarque;

    @NotNull(message = "Le modele est obligatoire")
    private String modele;

    @NotNull(message = "La taille est obligatoire")
    private String size;

    @NotNull(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "Le prix d'achat est obligatoire")
    private Double price;

    @NotNull(message = "Le prix de vente est obligatoire")
    private Double salePrice;

    @NotNull(message = "La ROM est obligatoire")
    private String rom;

    @NotNull(message = "La RAM est obligatoire")
    private String ram;

    @NotNull(message = "Le type de systeme est obligatoire")
    private String systemExp;


    private int idTypeTelephone;

    @NotNull(message = "Le pourcentage de reduction est obligatoire")
    private Double discount;

    @NotNull(message = "Les details sont obligatoires")
    private String shortDetails;

    @NotNull(message = "Le stock disponible est obligatoire")
    private int stock;

    @NotNull(message = "")
    private Boolean bestSeller;

    @NotNull(message = "")
    private Boolean showInHomePage;
    private Boolean sale;
    private String category;
    private int idCategory;
    private Boolean featured;

    @Transient
    private List<Image> images;

    @Transient
    private String brand;

    private String etat;

}

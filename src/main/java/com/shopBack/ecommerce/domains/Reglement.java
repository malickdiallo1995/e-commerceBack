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
public class Reglement extends BaseEntity {

    @NotNull(message = "La date de réglement est obligatoire")
    private Timestamp date;

    @NotNull(message = "Le montant réglé est obligatoire")
    private Double montat;

    private int idQuittance;

    private int idmodeReglement;

    private int idClient;

    @NotNull(message = "la devise est obligatoire")
    private String devise;
}

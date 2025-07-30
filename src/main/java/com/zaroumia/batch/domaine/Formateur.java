package com.zaroumia.batch.domaine;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@ToString
public class Formateur implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private String adresseEmail;
}

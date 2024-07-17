package com.example.gestionBancaire.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Utilisateur")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"role", "compte"})
public class Utilisateur {
    @Id
    @GeneratedValue
    private Integer id_user;

    @Column(name = "nom")
    private String nom;

    @Column (name = "prenom")
    private String prenom;

    @Column (name = "password")
    private String password;
    @Column (name = "phoneNumber")
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "utilisateur")
    private List<Compte> compte;

    @OneToMany(mappedBy = "emetteur")
    private List<Historique> historiquesEmetteur;

    @OneToMany(mappedBy = "recepteurMessage")
    private List<Messagerie> messageriesRecepteur;

    @OneToMany(mappedBy = "emetteurMessage")
    private List<Messagerie> messageriesEmetteur;



}

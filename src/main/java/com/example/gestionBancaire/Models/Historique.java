package com.example.gestionBancaire.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "Historique")
@Getter
@Setter
@NoArgsConstructor
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_historique;
    private double montant;
    private Date dateTransaction;
    private String typeTransaction;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "compte_emetteur")
    private Compte emetteur_compte;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "compte_recepteur")
    private Compte recepteur_compte;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "emetteur_id")
    private Utilisateur emetteur;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recepteur_id")
    private Utilisateur recepteur;

    // Getters et Setters
}


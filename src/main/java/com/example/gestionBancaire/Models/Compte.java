package com.example.gestionBancaire.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Compte")
@Getter
@Setter
@NoArgsConstructor
public class Compte {
    @Id
    @GeneratedValue
    private Integer id_compte;

    @Column(name = "numéroCompte")
    private int numeroCompte;

    @Column (name = "solde")
    private double solde;
    private boolean etat;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "emetteur_compte")
    private List<Historique> historiquesEmetteur;
    @OneToMany(mappedBy = "recepteur_compte")
    private List<Historique> historiquesRecepteur;

}


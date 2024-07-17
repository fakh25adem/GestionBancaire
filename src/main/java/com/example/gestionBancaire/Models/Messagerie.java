package com.example.gestionBancaire.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Messagerie")
@Getter
@Setter
@NoArgsConstructor
public class Messagerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_message;
    private String object;
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "emetteurMessage")
    private Utilisateur emetteurMessage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recepteurMessage")
    private Utilisateur recepteurMessage;

    // Getters et Setters
}


package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Models.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface HistoriqueService  {
    List <Historique> select();


    List <Historique> selectByemetteur(Optional<Utilisateur> utilisateur);
}

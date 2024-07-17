package com.example.gestionBancaire.Reposotiry;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoHistorique extends JpaRepository<Historique, Integer> {

    List<Historique> findByemetteur(Optional<Utilisateur> utilisateur);
}

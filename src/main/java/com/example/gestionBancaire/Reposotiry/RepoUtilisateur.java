package com.example.gestionBancaire.Reposotiry;

import com.example.gestionBancaire.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUtilisateur extends JpaRepository<Utilisateur, Integer> {
}

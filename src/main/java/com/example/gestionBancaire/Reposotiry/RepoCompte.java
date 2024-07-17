package com.example.gestionBancaire.Reposotiry;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoCompte extends JpaRepository<Compte, Integer> {

    Optional<Compte> findBynumeroCompte(int numeroCompte);

}

package com.example.gestionBancaire.Reposotiry;

import com.example.gestionBancaire.Models.Messagerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoMessagerie extends JpaRepository<Messagerie, Integer> {
}

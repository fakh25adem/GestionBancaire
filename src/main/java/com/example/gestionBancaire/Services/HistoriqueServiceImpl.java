package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoHistorique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoriqueServiceImpl implements HistoriqueService {
    @Autowired
    RepoHistorique repoHistorique;

    @Override
    public List<Historique> select() {
        return repoHistorique.findAll();
    }

    @Override
    public List<Historique> selectByemetteur( Optional<Utilisateur> utilisateur) {
        return repoHistorique.findByemetteur(utilisateur);
    }
}

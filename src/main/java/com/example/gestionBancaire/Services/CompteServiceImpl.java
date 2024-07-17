package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Reposotiry.RepoCompte;
import com.example.gestionBancaire.Reposotiry.RepoHistorique;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.Optional;
@Service
public class CompteServiceImpl implements CompteService {
    @Autowired
    private RepoUtilisateur repoUtilisateur;
    @Autowired
    private RepoCompte compteRepository;

    @Autowired
    private RepoHistorique repoHistorique;
    @Override
    public Compte desactiverCompte(int numeroCompte) {
        Optional<Compte> compte = compteRepository.findBynumeroCompte(numeroCompte);

        if (compte.isPresent()) {

            Compte compteToUpdate = compte.get();
            compteToUpdate.setEtat(false);

            return compteRepository.save(compteToUpdate);
        } else {
            return null;
        }
    }
    @Override
    public Compte Versement(int numeroCompte, double montant) {
        Optional<Compte> optionalCompte = compteRepository.findBynumeroCompte(numeroCompte);

        if (optionalCompte.isPresent() && montant > 0) {
            Compte compte = optionalCompte.get();
            compte.setSolde( compte.getSolde() + montant);

            Historique historique = new Historique();
            historique.setMontant(montant);
            historique.setDateTransaction(new Date()); // Date actuelle
            historique.setTypeTransaction("Versement");
            historique.setRecepteur_compte(compte);
            historique.setEmetteur(compte.getUtilisateur());
            repoHistorique.save(historique);
            compteRepository.save(compte);
            return compte;
        } else {
            System.out.println("compte vide ou montant < 0");
            return null;
        }
    }
    @Override
    public Pair<Compte, Compte> Virrement (int numeroCompte1, int numeroCompte2, double montant) {
        Optional<Compte> optionalCompte1 = compteRepository.findBynumeroCompte(numeroCompte1);
        Optional<Compte> optionalCompte2 = compteRepository.findBynumeroCompte(numeroCompte2);

        if (optionalCompte1.isPresent() && optionalCompte2.isPresent() && montant > 0) {
            Compte compte1 = optionalCompte1.get();
            Compte compte2 = optionalCompte2.get();
            compte1.setSolde(compte1.getSolde() - montant);
            compte2.setSolde(compte2.getSolde() + montant);
            compteRepository.save(compte1);
            compteRepository.save(compte2);

            Historique historique = new Historique();
            historique.setMontant(montant);
            historique.setDateTransaction(new Date()); // Date actuelle
            historique.setTypeTransaction("Virrement");
            historique.setEmetteur(compte1.getUtilisateur());
            historique.setRecepteur(compte2.getUtilisateur());
            historique.setRecepteur_compte(compte2);
            historique.setEmetteur_compte(compte1);
            repoHistorique.save(historique);

            return Pair.of(compte1, compte2);
        } else {
            System.out.println("Compte vide ou montant invalide");
            return null; // Or another indication according to your application's context
        }
    }

}

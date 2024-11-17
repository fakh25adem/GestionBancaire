package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.ModelDTO.PasswordDTO;
import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Role;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoCompte;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    private RepoUtilisateur repoUtilisateur;
    @Autowired
    private RepoCompte repoCompte;

    @Override
    public Utilisateur creer(Utilisateur utilisateur) {
        Role role = new Role();
        role.setId(2);
        utilisateur.setRole(role);
        Utilisateur utilisateur1= repoUtilisateur.save(utilisateur);
        Random rand = new Random();
        int numeroCompte = 10_000_000 + rand.nextInt(90_000_000);
        Compte compte = new Compte();
        compte.setNumeroCompte(numeroCompte);
        compte.setSolde(2000);
        compte.setEtat(true);
        compte.setUtilisateur(utilisateur1);
        repoCompte.save(compte);
        return utilisateur1;
    }

    @Override
    public Utilisateur updatePassword(Integer id, PasswordDTO newPassword) {

        Optional<Utilisateur> utilisateurById = repoUtilisateur.findById(id);

        if (utilisateurById.isPresent()) {
            Utilisateur utilisateur = utilisateurById.get();
            utilisateur.setPassword(newPassword.getPassword());
            return repoUtilisateur.save(utilisateur);

        } else {
            return null;
        }
    }

    @Override
    public Utilisateur modifierUtilisateur(Integer iduser, Utilisateur utilisateur) {
        System.out.println("modifier");
        Optional<Utilisateur> utilisateurById = repoUtilisateur.findById(iduser);

        if (iduser != 1) {
            if (utilisateurById.isPresent() && iduser == utilisateurById.get().getId_user()) {
                Utilisateur utilisateurToUpdate = utilisateurById.get();
                utilisateurToUpdate.setNom(utilisateur.getNom());
                utilisateurToUpdate.setPrenom(utilisateur.getPrenom());
                utilisateurToUpdate.setPhoneNumber(utilisateur.getPhoneNumber());
                repoUtilisateur.save(utilisateurToUpdate);

            } else {
                System.out.println("client introuvable");
                return null;
            }
        }
        else {
            if (utilisateurById.isPresent()) {
                Utilisateur utilisateurToUpdate = utilisateurById.get();
                utilisateurToUpdate.setNom(utilisateur.getNom());
                utilisateurToUpdate.setPrenom(utilisateur.getPrenom());
                utilisateurToUpdate.setPhoneNumber(utilisateur.getPhoneNumber());
                repoUtilisateur.save(utilisateurToUpdate);

            } else {
                System.out.println("pas d'amnistrateur pour ce site ");
             return null;
            }
        }
        return utilisateur;
    }

    @Override
    public List<Utilisateur> FindAllUser() {
        return repoUtilisateur.findAll();
    }

    @Override
    public void deleteUser(Integer id) {repoUtilisateur.deleteById(id);}

}

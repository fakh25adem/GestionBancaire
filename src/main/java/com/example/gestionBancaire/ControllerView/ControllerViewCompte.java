package com.example.gestionBancaire.ControllerView;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Reposotiry.RepoCompte;
import com.example.gestionBancaire.Reposotiry.RepoHistorique;
import com.example.gestionBancaire.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/compte")
public class ControllerViewCompte {
    @Autowired
    private RepoCompte repoCompte;
    @Autowired
    private RepoHistorique repoHistorique;
    @Autowired
    private CompteService CompteService;
    @GetMapping( "/all" )
    public String listCompte(Model model) {
        List<Compte> listCompte = repoCompte.findAll();
        model.addAttribute("listCompte",listCompte);
        return "/AllCompte";
    }
    @GetMapping( "/desactiver/{numeroCompte}" )
    public String desactiver(Model model,@PathVariable("numeroCompte") Integer numeroCompte) {
        CompteService.desactiverCompte(numeroCompte);
        return "redirect:/compte/all";
    }
    @GetMapping("/versementForm")
    public String showVersementForm(Model model) {
        // Récupérer la liste des comptes depuis le service
        List<Compte> comptes = repoCompte.findAll();


        // Ajouter la liste des comptes au modèle pour affichage dans le formulaire
        model.addAttribute("comptes", comptes);

        return "versementForm"; // Nom du fichier HTML (sans extension)
    }
    @PostMapping("/virementAdd")
    public String updateSoldeVirement(
            @RequestParam("numeroCompte1") int numeroCompte1,
            @RequestParam("numeroCompte2") int numeroCompte2,
            @RequestParam("montant") double montant,
            Model model) {
        try {
            // Rechercher le compte source
            Optional<Compte> optionalCompteSource = repoCompte.findBynumeroCompte(numeroCompte1);
            // Rechercher le compte destination
            Optional<Compte> optionalCompteDestination = repoCompte.findBynumeroCompte(numeroCompte2);

            if (optionalCompteSource.isPresent() && optionalCompteDestination.isPresent()) {
                Compte compteSource = optionalCompteSource.get();
                Compte compteDestination = optionalCompteDestination.get();

                if (compteSource.getSolde() >= montant) {// Mise à jour des soldes
                    compteSource.setSolde(compteSource.getSolde() - montant);
                    compteDestination.setSolde(compteDestination.getSolde() + montant);
                    Historique historique = new Historique();
                    historique.setMontant(montant);
                    historique.setDateTransaction(new Date()); // Date actuelle
                    historique.setTypeTransaction("Virrement");
                    historique.setEmetteur(compteSource.getUtilisateur());
                    historique.setRecepteur(compteDestination.getUtilisateur());
                    historique.setRecepteur_compte(compteDestination);
                    historique.setEmetteur_compte(compteDestination);
                    repoHistorique.save(historique);
                    repoCompte.save(compteSource);
                    repoCompte.save(compteDestination);

                } else {
                    model.addAttribute("message", "Erreur : Solde insuffisant sur le compte source.");
                }
            } else {
                model.addAttribute("message", "Erreur : L'un des comptes n'existe pas.");
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Une erreur est survenue : " + e.getMessage());
        }

        return "redirect:/MVC/Historique/All"; // Redirection vers la liste des comptes ou une vue appropriée
    }

    @GetMapping("/virementForm")
    public String showVirementForm(Model model) {
        // Récupérer la liste des comptes depuis le service
        List<Compte> comptes = repoCompte.findAll();


        // Ajouter la liste des comptes au modèle pour affichage dans le formulaire
        model.addAttribute("comptes", comptes);

        return "virement"; // Nom du fichier HTML (sans extension)
    }
    @PostMapping("/versementAdd")
    public String updateSolde(
            @RequestParam("numeroCompte") int numeroCompte,
            @RequestParam("montant") double montant,
            Model model) {
        try {
            // Rechercher le compte correspondant
            Optional<Compte> optionalCompte = repoCompte.findBynumeroCompte(numeroCompte);

            if (optionalCompte.isPresent()) {
                // Mise à jour du solde
                Compte compte = optionalCompte.get();
                compte.setSolde(compte.getSolde() + montant);
                repoCompte.save(compte);
                Historique historique = new Historique();
                historique.setMontant(montant);
                historique.setDateTransaction(new Date()); // Date actuelle
                historique.setTypeTransaction("Versement");
                historique.setRecepteur_compte(compte);
                historique.setEmetteur(compte.getUtilisateur());
                repoHistorique.save(historique);


                model.addAttribute("message", "Versement réussi ! Le compte " +
                        compte.getNumeroCompte() + " a maintenant un solde de " + compte.getSolde());
            } else {
                model.addAttribute("message", "Erreur : Compte non trouvé.");
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", "Une erreur est survenue : " + e.getMessage());
        }

        return "redirect:/MVC/Historique/All"; // Fichier Thymeleaf pour afficher le résultat
    }


}

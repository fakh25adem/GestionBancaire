package com.example.gestionBancaire.ControllerView;

import com.example.gestionBancaire.ModelDTO.PasswordDTO;
import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoCompte;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import com.example.gestionBancaire.Services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class ControllerViewUtilisateur {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private RepoCompte repoCompte;
    @Autowired
    private RepoUtilisateur repoUtilisateur;

    // Route pour afficher la page de création d'utilisateur
    @GetMapping("/save")
    public String afficherCreationUtilisateur(Model model) {
        Utilisateur user = new Utilisateur() ;
        model.addAttribute("User", user);

        return "createUtilisateur"; // Nom du template Thymeleaf pour la création
    }

    // Route pour traiter la création d'un utilisateur
    @PostMapping("/savePost")
    public String creationUtilisateur(@ModelAttribute("User") Utilisateur utilisateur) {
            utilisateurService.creer(utilisateur);
            return "redirect:/user/all";


    }
    @GetMapping( "/delete/{id}" )
    public String deleteUser(@PathVariable("id") Integer id) {
        Optional<Utilisateur> user =repoUtilisateur.findById(id);
        List<Compte> comptes = repoCompte.findByUtilisateur(user);
        repoCompte.deleteAll(comptes);
        utilisateurService.deleteUser(id);

        return "redirect:/user/all";
    }
    @GetMapping( "/all" )
    public String listProduct(Model model) {
        List<Utilisateur> listUtilisateur = utilisateurService.FindAllUser();
        model.addAttribute("listUtilisateur",listUtilisateur);
        return "/AllUser";
    }

    // Route pour afficher la page de mise à jour du mot de passe
    @GetMapping("/editPassword/{id}")
    public String afficherUpdatePassword(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return "updatePassword"; // Nom du template pour la mise à jour du mot de passe
    }

    // Route pour traiter la mise à jour du mot de passe
    @PostMapping("/editPassword/{id}")
    public String updatePassword(@PathVariable Integer id, @ModelAttribute PasswordDTO newPassword, Model model) {
        try {
            Utilisateur utilisateur = utilisateurService.updatePassword(id, newPassword);

            if (utilisateur != null) {
                model.addAttribute("message", "Mot de passe mis à jour avec succès !");
                model.addAttribute("utilisateur", utilisateur);
            }
            return "AllUser"; // Nom du template Thymeleaf pour afficher le résultat
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
            model.addAttribute("message", "Une erreur s'est produite lors de la mise à jour du mot de passe.");
            return "AllUser"; // Retourne à la page de résultat avec le message d'erreur
        }
    }

    // Route pour afficher la page de mise à jour d'utilisateur
    @GetMapping("/editUtilisateur/{iduser}")
    public String afficherUpdateUtilisateur(@PathVariable Integer iduser, Model model) {
        model.addAttribute("iduser", iduser);
        return "updateUtilisateur"; // Nom du template pour la mise à jour d'utilisateur
    }

    // Route pour traiter la mise à jour d'un utilisateur
    @PostMapping("/editUtilisateur/{iduser}")
    public String updateUtilisateur(@PathVariable Integer iduser, @ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            Utilisateur updatedUtilisateur = utilisateurService.modifierUtilisateur(iduser, utilisateur);

            if (updatedUtilisateur != null) {
                model.addAttribute("message", "Mise à jour de l'utilisateur réussie !");
                model.addAttribute("utilisateur", updatedUtilisateur);
            }
            return "AllUser"; // Nom du template Thymeleaf pour afficher le résultat
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
            model.addAttribute("message", "Une erreur s'est produite lors de la mise à jour de l'utilisateur.");
            return "AllUser"; // Retourne à la page de résultat avec le message d'erreur
        }
    }

}



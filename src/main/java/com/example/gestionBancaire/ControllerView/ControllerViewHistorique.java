package com.example.gestionBancaire.ControllerView;

import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoHistorique;
import com.example.gestionBancaire.Services.HistoriqueService;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/MVC/Historique")
public class ControllerViewHistorique {
    @Autowired
    private RepoHistorique historiqueRepo;

    @Autowired
    private HistoriqueService historiqueService;

    @Autowired
    private RepoUtilisateur utilisateurRepo;

    @GetMapping("/All")
    public String select(Model model) {
        try {
            List<Historique> listHistoriques = historiqueRepo.findAll(); // Fetch all historiques
            model.addAttribute("listHistoriques", listHistoriques);       // Add data to the model
            return "historique/all";                                     // Return the view name
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/AllById/{idCompte}")
    public String selectIdCompte(@PathVariable int idCompte, Model model) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurRepo.findById(idCompte);

            if (utilisateur.isPresent()) {
                model.addAttribute("message", "Get all historique by ID successfully");
                model.addAttribute("data", historiqueService.selectByemetteur(utilisateur));
            } else {
                model.addAttribute("message", "No user found with ID " + idCompte);
            }
            return "historique/byId"; // Corresponds to historique/byId.html in templates folder
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "error";
        }
    }
}

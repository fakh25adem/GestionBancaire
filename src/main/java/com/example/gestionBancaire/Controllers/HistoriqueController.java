package com.example.gestionBancaire.Controllers;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Models.Historique;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoCompte;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import com.example.gestionBancaire.Services.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Historique")
public class HistoriqueController {
    @Autowired
    HistoriqueService historiqueService;
    @Autowired
    RepoUtilisateur utilisateurRepo;
    @GetMapping("/All")
    public ResponseEntity<Object> select() {
        try {

            Map<String, Object> response = new HashMap<>();
            response.put("message : ", "getAll histrorique with successful");
            response.put("data : ", historiqueService.select());

            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/AllById/{idCompte}")
    public ResponseEntity<Object> selectIdCompte(@PathVariable int idCompte) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurRepo.findById(idCompte);

            Map<String, Object> response = new HashMap<>();
            response.put("message : ", "getAllById histrorique with successful");
            response.put("data : ", historiqueService.selectByemetteur(utilisateur));

            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}

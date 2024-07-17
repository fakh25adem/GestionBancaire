package com.example.gestionBancaire.Controllers;

//import com.example.gestionBancaire.ModelDTO.EmailDTO;
import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Services.CompteService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Compte")
public class CompteController {
    @Autowired
    private CompteService compteService;
    @PutMapping ("/desactiver/{numeroCompte}")
    public ResponseEntity<Object> désactiverCompte(@PathVariable int numeroCompte) {
        try {
            Compte compte =compteService.desactiverCompte(numeroCompte);
            Map<String, Object> response = new HashMap<>();

            if( compte!=null) {
                response.put("message : ", "Account desctivation successful");
                response.put("data : ",  compte);
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @PutMapping("/versement")
    public ResponseEntity<?> versement(@RequestParam("numeroCompte")  int codeCompte1,
                                           @RequestParam("montant") double montant)
    {
        try {
            System.out.println("codeCompte1 "+ codeCompte1);
            System.out.println("montant "+ montant);
            Compte compte = compteService.Versement(codeCompte1, montant);
            Map<String, Object> response = new HashMap<>();

            if (compte != null) {
                return ResponseEntity.ok("Versement réussi:\n" +
                        "numreoCompte  est : " + compte.getNumeroCompte()
                        +" son nouveau solde : " + compte.getSolde() ) ;

            }else {
                return ResponseEntity.badRequest().body("Compte vide ou montant invalide");
            }

        } catch (RuntimeException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }

    }
    @PutMapping("/virement")
    public ResponseEntity<?> virement(
            @RequestParam("numeroCompte1") int numeroCompte1,
            @RequestParam("numeroCompte2") int numeroCompte2,
            @RequestParam("montant") double montant) {
        try {
            Pair<Compte, Compte> result = compteService.Virrement(numeroCompte1, numeroCompte2, montant);

            if (result != null) {

                return ResponseEntity.ok("Virement réussi:\n" +
                        "numreoCompte 1 est : " + result.getLeft().getNumeroCompte()
                        +" son nouveau solde : " + result.getLeft().getSolde() + "\n" +
                        "numeroCompte 2: " + result.getRight().getNumeroCompte()
                        +" son nouveau solde : " + result.getRight().getSolde());
            } else {
                return ResponseEntity.badRequest().body("Compte vide ou montant invalide");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}

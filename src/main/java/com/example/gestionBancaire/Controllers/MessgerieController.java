package com.example.gestionBancaire.Controllers;
import com.example.gestionBancaire.ModelDTO.MessagerieDTO;
import com.example.gestionBancaire.Models.Messagerie;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoMessagerie;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import com.example.gestionBancaire.Services.MessagerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Messagerie")
public class MessgerieController {
    @Autowired
    MessagerieService messagerieService;
    @Autowired
    RepoUtilisateur repoUtilisateur;

    @PostMapping("/create")
    public ResponseEntity<Object> creationMessage (@RequestBody MessagerieDTO messagerie) {
        try {
            Map<String, Object> response = new HashMap<>();
            Optional<Utilisateur> userEmmeteur = repoUtilisateur.findById(messagerie.getEmetteur());
            Optional<Utilisateur> userRecepteur = repoUtilisateur.findById(messagerie.getRecepteur());
            if(userRecepteur.isPresent() && userEmmeteur.isPresent()){
                Messagerie messagerie1 = new Messagerie();
                messagerie1.setDescription(messagerie.getDescription());
                messagerie1.setObject(messagerie.getObject());
                messagerie1.setEmetteurMessage(userEmmeteur.get());
                messagerie1.setRecepteurMessage(userRecepteur.get());
                Messagerie saveMessagerie=messagerieService.insertMessage(messagerie1);

                if( saveMessagerie!=null) {
                    response.put("message : ", "insert messagerie successful");
                    response.put("data : ",  saveMessagerie);
                }else {
                    System.out.println("viideeeee saveMessagerie");
                }

            }else {
                System.out.println("viideeeee userrrrrrr");

            }

            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());}
    }
}

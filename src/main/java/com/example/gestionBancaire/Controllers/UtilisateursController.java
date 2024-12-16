package com.example.gestionBancaire.Controllers;
import com.example.gestionBancaire.Config.JwtUtils;
import com.example.gestionBancaire.ModelDTO.JwtResponse;
import com.example.gestionBancaire.ModelDTO.Login;
import com.example.gestionBancaire.ModelDTO.PasswordDTO;
import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import com.example.gestionBancaire.Services.UtilisateurService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/User")

public class UtilisateursController  {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    RepoUtilisateur userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<Object> creationUtilisateur (@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur utilisateur1=utilisateurService.creer(utilisateur);
            Map<String, Object> response = new HashMap<>();

            if( utilisateur1!=null) {
                response.put("message : ", "create user successful");
                response.put("data : ",  utilisateur1);
            }
            return ResponseEntity.status(200).body(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());}
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<Object> updatePassword(@PathVariable Integer id, @RequestBody PasswordDTO newPassword) {
        try {
            Utilisateur passwordUpdate =utilisateurService.updatePassword(id, newPassword);
            Map<String, Object> response = new HashMap<>();

            if( passwordUpdate!=null) {
                response.put("message : ", "update password successful");
                response.put("data : ",  passwordUpdate);
            }
            return ResponseEntity.status(200).body(response);

        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }

    }
    @PutMapping("/updateUtilisateur/{iduser}")
    public ResponseEntity<Object> update(@PathVariable Integer iduser, @RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur updatedUtilisateur = utilisateurService.modifierUtilisateur(iduser, utilisateur);
            Map<String, Object> response = new HashMap<>();
            if (updatedUtilisateur != null) {
                response.put("message : ", "update user successful");
                response.put("data : ", updatedUtilisateur);
            }
            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @PostMapping ("/up")
    public ResponseEntity<?> authenticateUser(@RequestBody Login loginRequest) {

        try {


            Utilisateur utilisateur = userRepository.findBynom(loginRequest.getNom()); // Assurez-vous d'utiliser le bon champ
            if (utilisateur == null) {
                System.out.println("Utilisateur non trouvé : " + utilisateur.getNom());
            }
            if (!passwordEncoder.matches(loginRequest.getMdp(), utilisateur.getPassword())) {
                System.out.println("Mot de passe incorrect");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNom(), loginRequest.getMdp()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid user ou mot de passe");
        }

    }
}



package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.ModelDTO.PasswordDTO;
import com.example.gestionBancaire.Models.Utilisateur;

import java.util.List;

public interface UtilisateurService {

     Utilisateur creer(Utilisateur utilisateur);
     Utilisateur updatePassword(Integer id, PasswordDTO newPassword);
     Utilisateur modifierUtilisateur(Integer iduser, Utilisateur utilisateur);
     List<Utilisateur> FindAllUser();
     void deleteUser(Integer id );


}
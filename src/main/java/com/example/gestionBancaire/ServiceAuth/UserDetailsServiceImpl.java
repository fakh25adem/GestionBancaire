package com.example.gestionBancaire.ServiceAuth;


import com.example.gestionBancaire.Models.Utilisateur;
import com.example.gestionBancaire.Reposotiry.RepoUtilisateur;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    RepoUtilisateur userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nom) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findBynom(nom); // Assurez-vous d'utiliser le bon champ

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + nom);
        }
        return UserDetailsImpl.build(utilisateur);
    }
}
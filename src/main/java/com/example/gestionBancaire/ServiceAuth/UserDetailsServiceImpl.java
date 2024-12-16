package com.example.Client.ServiceAuth;

import com.example.Client.Model.Personne;
import com.example.Client.Repository.PersonneRepository;
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

    PersonneRepository personneRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nom) throws UsernameNotFoundException {
        Personne personne = personneRepository.findByEmail(nom);
        return UserDetailsImpl.build(personne);
    }
}
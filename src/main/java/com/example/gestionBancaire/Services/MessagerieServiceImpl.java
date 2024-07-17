package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.Models.Messagerie;
import com.example.gestionBancaire.Reposotiry.RepoMessagerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagerieServiceImpl implements  MessagerieService{
    @Autowired
    RepoMessagerie repoMessagerie;
    @Override
    public Messagerie insertMessage(Messagerie messagerie) {
        return repoMessagerie.save(messagerie);
    }
}

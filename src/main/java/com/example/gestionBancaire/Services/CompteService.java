package com.example.gestionBancaire.Services;

import com.example.gestionBancaire.Models.Compte;
import org.apache.commons.lang3.tuple.Pair;


public interface CompteService {
    Compte desactiverCompte(int numeroCompte );

    Compte Versement (int numeroCompte, double montant);

    Pair<Compte, Compte> Virrement (int numeroCompte1, int numeroCompte2, double montant);
}

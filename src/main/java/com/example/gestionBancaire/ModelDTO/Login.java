package com.example.gestionBancaire.ModelDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Login {
    private String nom;
    private String mdp;
}

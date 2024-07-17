package com.example.gestionBancaire.ModelDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessagerieDTO {
    private String object;
    private String description;
    private int emetteur;
    private int recepteur;
}

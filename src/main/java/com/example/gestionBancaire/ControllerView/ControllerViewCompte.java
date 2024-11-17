package com.example.gestionBancaire.ControllerView;

import com.example.gestionBancaire.Models.Compte;
import com.example.gestionBancaire.Reposotiry.RepoCompte;
import com.example.gestionBancaire.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/compte")
public class ControllerViewCompte {
    @Autowired
    private RepoCompte repoCompte;
    @Autowired
    private CompteService CompteService;
    @GetMapping( "/all" )
    public String listCompte(Model model) {
        List<Compte> listCompte = repoCompte.findAll();
        model.addAttribute("listCompte",listCompte);
        return "/AllCompte";
    }
    @GetMapping( "/desactiver/{numeroCompte}" )
    public String desactiver(Model model,@PathVariable("numeroCompte") Integer numeroCompte) {
        CompteService.desactiverCompte(numeroCompte);
        return "redirect:/compte/all";
    }
}

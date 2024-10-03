package friendsofmine.m2.controllers;

import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping(value = "/utilisateurs")
    public String list(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.findAllUtilisateurs());
        return "utilisateurs";
    }

    @GetMapping("utilisateur/search")
    public String searchUtilisateurs(@RequestParam(value = "sexe", required = true) String sexe, Model model) {
        List<Utilisateur> util;
        if (sexe.equals("M"))
            util = utilisateurService.findUtilisateursM();
        else if (sexe.equals("F"))
            util = utilisateurService.findUtilisateursF();
        else
            util = utilisateurService.findAllUtilisateurs();
        model.addAttribute("utilisateurs", util);
        return "utilisateurs";
    }
}
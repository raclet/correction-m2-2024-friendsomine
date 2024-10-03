package friendsofmine.m2.controllers;

import friendsofmine.m2.domain.*;
import friendsofmine.m2.exceptions.InscriptionNotFoundException;
import friendsofmine.m2.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InscriptionController {

    @Autowired
    private ActiviteService activiteService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    InscriptionService inscriptionService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/inscription", produces="application/json;charset=UTF-8")
    public Inscription addInscription(@RequestParam(value = "utilisateur_id") Long utilisateurId, @RequestParam(value = "activite_id") Long activiteId) {
        Activite activite = activiteService.findActiviteById(activiteId);
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(utilisateurId);
        Inscription inscription = new Inscription(utilisateur, activite);
        return inscriptionService.saveInscription(inscription);
    }

    @GetMapping(value = "api/inscription/{id}", produces="application/json;charset=UTF-8")
    public Inscription showInscription(@PathVariable Long id){
        Inscription ins = inscriptionService.findInscriptionById(id);
        if (ins == null)
            throw new InscriptionNotFoundException(id);
        return ins;
    }

    @DeleteMapping(value = "/api/inscription/{id}")
    public void deleteInscription(@PathVariable("id") Long inscriptionId) {
        inscriptionService.deleteInscription(inscriptionId);
    }

    public ActiviteService getActiviteService() {
        return activiteService;
    }

    public void setActiviteService(ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public InscriptionService getInscriptionService() {
        return inscriptionService;
    }

    public void setInscriptionService(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }
}

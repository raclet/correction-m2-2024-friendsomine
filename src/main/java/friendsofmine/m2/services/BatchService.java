package friendsofmine.m2.services;

import friendsofmine.m2.domain.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchService {

    private final Utilisateur u1, u2, u3;

    @Autowired
    private UtilisateurService utilisateurService;

    public BatchService() {
        u1 = new Utilisateur("nom1", "prenom1", "u1@u1.fr", "M");
        u2 = new Utilisateur("nom2", "prenom2", "u2@u1.fr", "M");
        u3 = new Utilisateur("nom3", "prenom3", "u3@u1.fr", "M");
    }

    private Utilisateur saveU1() {
        return utilisateurService.saveUtilisateur(u1);
    }

    private Utilisateur saveU2() {
        return utilisateurService.saveUtilisateur(u2);
    }

    private Utilisateur saveU3() {
        return utilisateurService.saveUtilisateur(u3);
    }

    @Transactional
    public void saveUsAll() {
        saveU1();
        saveU2();
        u3.setNom(null); // échec volontaire, violation d'une contrainte de validation
        saveU3();        // exception déclenchée en conséquence à cette ligne
    }

}

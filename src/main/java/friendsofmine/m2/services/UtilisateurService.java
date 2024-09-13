package friendsofmine.m2.services;

import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository ;

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null)
            throw new IllegalArgumentException();
        return utilisateurRepository.save(utilisateur) ;
    }

    public Utilisateur findUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public long countUtilisateur() {
        return utilisateurRepository.count();
    }

    public UtilisateurRepository getUtilisateurRepository() {
        return utilisateurRepository;
    }

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
}

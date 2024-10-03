package friendsofmine.m2.services;

import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository ;

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null)
            throw new IllegalArgumentException();
        return utilisateurRepository.save(utilisateur) ;
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        utilisateur.getActivites().forEach(activite -> activite.setResponsable(null));
        utilisateurRepository.delete(utilisateur);
    }

    public Utilisateur findUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    @Cacheable("utilisateurs") // attention à l'import. Choisir org.springframework.cache.annotation.Cacheable
    public Utilisateur findUtilisateurByEmail(String email) {
        // simulate slow service
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return utilisateurRepository.findByEmail(email);
    }

    public long countUtilisateur() {
        return utilisateurRepository.count();
    }

    public ArrayList<Utilisateur> findAllUtilisateurs() {
        Iterable<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        ArrayList<Utilisateur> utilisateurList = convert(utilisateurs);
        utilisateurList.sort(Comparator.comparing(Utilisateur::getNom));
        return utilisateurList;
    }
    public ArrayList<Utilisateur> findUtilisateursM() {
        Utilisateur homme = new Utilisateur();
        homme.setSexe("M");
        Example<Utilisateur> example = Example.of(homme);
        return convert(utilisateurRepository.findAll(example));
    }
    public ArrayList<Utilisateur> findUtilisateursF() {
        Utilisateur femme = new Utilisateur();
        femme.setSexe("F");
        Example<Utilisateur> example = Example.of(femme);
        return convert(utilisateurRepository.findAll(example));
    }
    // convert from Iterable<Utilisateur> to AraryList<Utilisateur>
    private ArrayList<Utilisateur> convert(Iterable<Utilisateur> utilisateurs) {
        return StreamSupport.stream(utilisateurs.spliterator(), false)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public UtilisateurRepository getUtilisateurRepository() {
        return utilisateurRepository;
    }

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
}

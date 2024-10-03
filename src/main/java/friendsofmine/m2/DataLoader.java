package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Inscription;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.InscriptionService;
import friendsofmine.m2.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    private UtilisateurService utilisateurService;
    private InscriptionService inscriptionService;

    private Utilisateur thom, ed, karen, jules;
    private Activite guitare, muscu, poker, pingpong, jogging, philo, procrastination;
    private Inscription thomAuPingPong, thomALaProcrastination, edAuJogging, karenALaPhilo, karenAuPingPong;

    @Autowired
    public DataLoader(UtilisateurService utilS, InscriptionService insS) {
        utilisateurService = utilS;
        inscriptionService = insS;
    }

    public void run(ApplicationArguments args) {
        initUtilisateurs();
        initActivites();
        initInscriptions();
        saveUtilisateursAndActivitesAndInscriptions();
    }

    public void initUtilisateurs() {
        initThom();
        initEd();
        initKaren();
        initJulian();
    }

    private void initThom() {
        thom = new Utilisateur("Yorke", "Thom", "thom@rh.com", "M");
    }

    private void initEd() {
        ed = new Utilisateur("Obrien", "Ed", "ed@rh.com", "M");
    }

    private void initKaren() {
        karen = new Utilisateur("Orzolek", "Karen", "karen@yyy.com", "F");
    }

    private void initJulian() {
        jules = new Utilisateur("Casablancas", "Julian", "jc@ts.com", "M");
    }

    public void initActivites() {
        initGuitare();
        initMuscu();
        initPoker();
        initPingpong();
        initJogging();
        initPhilo();
        initProcrastination();
    }

    private void initGuitare() {
        guitare = new Activite("Guitare", "Matériel non fourni", thom);
    }

    private void initMuscu() {
        muscu = new Activite("Muscu", "Créneau réservé le mardi", ed);
    }

    private void initPoker() {
        poker = new Activite("Poker", "Petite blind à 1 euro", karen);
    }

    private void initPingpong() {
        pingpong = new Activite("Ping Pong", "Matériel non fourni", jules);
    }

    private void initJogging() {
        jogging = new Activite("Jogging", "Tous les midis", ed);
    }

    private void initPhilo() {
        philo = new Activite("Philo", "Le club des admirateurs de Socrate", thom);
    }

    private void initProcrastination() {
        procrastination = new Activite("Procrastination", "On verra demain", thom);
    }

    public void initInscriptions() {
        initThomAuPingPong();
        initThomALaProcrastination();
        initEdAuJogging();
        initKarenALaPhilo();
        initKarenAuPingPong();
    }

    private void initThomAuPingPong() {
        thomAuPingPong = new Inscription(thom, pingpong);
    }

    private void initThomALaProcrastination() {
        thomALaProcrastination = new Inscription(thom, procrastination);
    }

    private void initEdAuJogging() {
        edAuJogging = new Inscription(ed, jogging);
    }

    private void initKarenALaPhilo() {
        karenALaPhilo = new Inscription(karen, philo);
    }

    private void initKarenAuPingPong() {
        karenAuPingPong = new Inscription(karen, pingpong);
    }


    public void  saveUtilisateursAndActivitesAndInscriptions() {
        thom = utilisateurService.saveUtilisateur(thom);
        ed = utilisateurService.saveUtilisateur(ed);
        karen = utilisateurService.saveUtilisateur(karen);
        jules = utilisateurService.saveUtilisateur(jules);

        thomAuPingPong = inscriptionService.saveInscription(thomAuPingPong);
        thomALaProcrastination = inscriptionService.saveInscription(thomALaProcrastination);
        edAuJogging = inscriptionService.saveInscription(edAuJogging);
        karenALaPhilo = inscriptionService.saveInscription(karenALaPhilo);
        karenAuPingPong = inscriptionService.saveInscription(karenAuPingPong);
    }

    public Utilisateur getThom() {
        return thom;
    }

    public Utilisateur getEd() {
        return ed;
    }

    public Utilisateur getKaren() {
        return karen;
    }

    public Utilisateur getJulian() {
        return jules;
    }

    public Activite getGuitare() {
        return guitare;
    }

    public Activite getMuscu() {
        return muscu;
    }

    public Activite getPoker() {
        return poker;
    }

    public Activite getPingpong() {
        return pingpong;
    }

    public Activite getJogging() {
        return jogging;
    }

    public Activite getPhilo() {
        return philo;
    }

    public Activite getProcrastination() {
        return procrastination;
    }

    public Inscription getThomAuPingPong() {
        return thomAuPingPong;
    }

    public Inscription getThomALaProcrastination() {
        return thomALaProcrastination;
    }

    public Inscription getEdAuJogging() {
        return edAuJogging;
    }

    public Inscription getKarenALaPhilo() {
        return karenALaPhilo;
    }

    public Inscription getKarenAuPingPong() {
        return karenAuPingPong;
    }
}

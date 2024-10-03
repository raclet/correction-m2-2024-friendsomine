package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

    private ActiviteService activiteService;
    private UtilisateurService utilisateurService;
    private Utilisateur thom, ed, karen, jules;
    private Activite guitare, muscu, poker, pingpong, jogging, philo, procrastination;

    @Autowired
    public DataLoader(ActiviteService actS, UtilisateurService utilS) {
        activiteService = actS;
        utilisateurService = utilS;
    }

    public void run(ApplicationArguments args) {
        initUtilisateurs();
        initActivites();
        saveUtilisateursAndActivites();
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

    public void  saveUtilisateursAndActivites() {
        thom = utilisateurService.saveUtilisateur(thom);
        ed = utilisateurService.saveUtilisateur(ed);
        karen = utilisateurService.saveUtilisateur(karen);
        jules = utilisateurService.saveUtilisateur(jules);

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
}
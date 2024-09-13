package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
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
    }

    public void initUtilisateurs() {
        initThom();
        initEd();
        initKaren();
        initJulian();
    }

    private void initThom() {
        thom = utilisateurService.saveUtilisateur(new Utilisateur("Yorke", "Thom", "thom@rh.com", "M"));
    }

    private void initEd() {
        ed = utilisateurService.saveUtilisateur(new Utilisateur("Obrien", "Ed", "ed@rh.com", "M"));
    }

    private void initKaren() {
        karen = utilisateurService.saveUtilisateur(new Utilisateur("Orzolek", "Karen", "karen@yyy.com", "F"));
    }

    private void initJulian() {
        jules = utilisateurService.saveUtilisateur(new Utilisateur("Casablancas", "Julian", "jc@ts.com", "M"));
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
        guitare = activiteService.saveActivite(new Activite("Guitare", "Matériel non fourni"));
    }

    private void initMuscu() {
        muscu = activiteService.saveActivite(new Activite("Muscu", "Créneau réservé le mardi"));
    }

    private void initPoker() {
        poker = activiteService.saveActivite(new Activite("Poker", "Petite blind à 1 euro"));
    }

    private void initPingpong() {
        pingpong = activiteService.saveActivite(new Activite("Ping Pong", "Matériel non fourni"));
    }

    private void initJogging() {
        jogging = activiteService.saveActivite(new Activite("Jogging", "Tous les midis"));
    }

    private void initPhilo() {
        philo = activiteService.saveActivite(new Activite("Philo", "Le club des admirateurs de Socrate"));
    }

    private void initProcrastination() {
        procrastination = activiteService.saveActivite(new Activite("Procrastination", "On verra demain"));
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

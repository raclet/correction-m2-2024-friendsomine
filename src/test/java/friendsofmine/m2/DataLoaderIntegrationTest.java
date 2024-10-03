package friendsofmine.m2;

import friendsofmine.m2.domain.Inscription;
import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.services.InscriptionService;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class DataLoaderIntegrationTest {

    @Autowired
    private ActiviteService activiteService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private DataLoader dataLoader;

    @Test
    public void testCardinalUtilisateur() {
        // au démarrage de l'application, il y a 4 utilisateurs en base
        assertEquals(4, utilisateurService.countUtilisateur());
    }

    @Test
    public void testCardinalActivite() {
        // au démarrage de l'application, il y a 4 utilisateurs en base
        assertEquals(7, activiteService.countActivite());
    }

    @Test
    public void testCheckThom() {
        // l'utilisateur Thom Yorke est en base
        assertEquals("thom@rh.com", dataLoader.getThom().getEmail());
        assertEquals("Yorke", dataLoader.getThom().getNom());
        assertEquals("Thom", dataLoader.getThom().getPrenom());
        assertEquals("M", dataLoader.getThom().getSexe());
    }

    @Test
    public void testCheckEd() {
        // l'utilisateur Ed Obrien est en base
        assertEquals("ed@rh.com", dataLoader.getEd().getEmail());
        assertEquals("Obrien", dataLoader.getEd().getNom());
        assertEquals("Ed", dataLoader.getEd().getPrenom());
        assertEquals("M", dataLoader.getEd().getSexe());
    }

    @Test
    public void testCheckKaren() {
        // l'utilisattrice Karen Orzolek est en base
        assertEquals("karen@yyy.com", dataLoader.getKaren().getEmail());
        assertEquals("Orzolek", dataLoader.getKaren().getNom());
        assertEquals("Karen", dataLoader.getKaren().getPrenom());
        assertEquals("F", dataLoader.getKaren().getSexe());
    }

    @Test
    public void testCheckJulian() {
        // l'utilisateur Julian Casablancas est en base
        assertEquals("jc@ts.com", dataLoader.getJulian().getEmail());
        assertEquals("Casablancas", dataLoader.getJulian().getNom());
        assertEquals("Julian", dataLoader.getJulian().getPrenom());
        assertEquals("M", dataLoader.getJulian().getSexe());
    }

    @Test
    public void testGuitare() {
        // l'activité Guitare est en base
        assertEquals("Guitare", dataLoader.getGuitare().getTitre());
        assertEquals("Matériel non fourni", dataLoader.getGuitare().getDescriptif());
    }

    @Test
    public void testMuscu() {
        // l'activité Muscu est en base
        assertEquals("Muscu", dataLoader.getMuscu().getTitre());
        assertEquals("Créneau réservé le mardi", dataLoader.getMuscu().getDescriptif());
    }

    @Test
    public void testPoker() {
        // l'activité Poker est en base
        assertEquals("Poker", dataLoader.getPoker().getTitre());
        assertEquals("Petite blind à 1 euro", dataLoader.getPoker().getDescriptif());
    }

    @Test
    public void testPingPong() {
        // l'activité Ping-Pong est en base
        assertEquals("Ping Pong", dataLoader.getPingpong().getTitre());
        assertEquals("Matériel non fourni", dataLoader.getPingpong().getDescriptif());
    }

    @Test
    public void testJogging() {
        // l'activité Jogging est en base
        assertEquals("Jogging", dataLoader.getJogging().getTitre());
        assertEquals("Tous les midis", dataLoader.getJogging().getDescriptif());
    }

    @Test
    public void testPhilo() {
        // l'activité Philo est en base
        assertEquals("Philo", dataLoader.getPhilo().getTitre());
        assertEquals("Le club des admirateurs de Socrate", dataLoader.getPhilo().getDescriptif());
    }

    @Test
    public void testProcastination() {
        // l'activité Procrastination est en base
        assertEquals("Procrastination", dataLoader.getProcrastination().getTitre());
        assertEquals("On verra demain", dataLoader.getProcrastination().getDescriptif());
    }

    @Test
    public void testLesBonsResponsablesSontAffectesAuxActivites() {
        // Thom est responsable de l'activité "guitare"
        assertEquals(dataLoader.getThom().getId(), dataLoader.getGuitare().getResponsable().getId());
        // Karen est responsable de l'activité "poker"
        assertEquals(dataLoader.getKaren().getId(), dataLoader.getPoker().getResponsable().getId());
        // Julian est responsable de l'activité "ping pong"
        assertEquals(dataLoader.getJulian().getId(), dataLoader.getPingpong().getResponsable().getId());
        // Thom est responsable de l'activité "philo"
        assertEquals(dataLoader.getThom().getId(), dataLoader.getPhilo().getResponsable().getId());
        // Thom est responsable de l'activité "procrastination"
        assertEquals(dataLoader.getThom().getId(), dataLoader.getProcrastination().getResponsable().getId());
        // Ed est responsable de l'activité "jogging"
        assertEquals(dataLoader.getEd().getId(), dataLoader.getJogging().getResponsable().getId());
        // Ed est responsable de l'activité "muscu"
        assertEquals(dataLoader.getEd().getId(), dataLoader.getMuscu().getResponsable().getId());
    }

    @Test
    public void testCardinalInscription() {
        // au démarrage de l'application, il y a 5 inscriptions en base
        assertEquals(5, inscriptionService.countInscription());
    }

    @Test
    public void testThomEstInscritAuPingPong() {
        // Thom est inscrit au PingPong
        Inscription thomAuPingPong = dataLoader.getThomAuPingPong();
        assertEquals(dataLoader.getThom().getId(), thomAuPingPong.getParticipant().getId());
        assertEquals(dataLoader.getPingpong().getId(), thomAuPingPong.getActivite().getId());
    }

    @Test
    public void testThomEstInscritALaProcrastination() {
        // Thom est inscrit à la procrastination
        Inscription thomALaPro = dataLoader.getThomALaProcrastination();
        assertEquals(dataLoader.getThom().getId(), thomALaPro.getParticipant().getId());
        assertEquals(dataLoader.getProcrastination().getId(), thomALaPro.getActivite().getId());
    }

    @Test
    public void testEdEstInscritAuJogging() {
        // Ed est inscrit au Jogging
        Inscription edAuJogging = dataLoader.getEdAuJogging();
        assertEquals(dataLoader.getEd().getId(), edAuJogging.getParticipant().getId());
        assertEquals(dataLoader.getJogging().getId(), edAuJogging.getActivite().getId());
    }

    @Test
    public void testKarenEstInscriteALAPhilo() {
        // Karen est inscrite à la Philo
        Inscription karenALaPhilo = dataLoader.getKarenALaPhilo();
        assertEquals(dataLoader.getKaren().getId(), karenALaPhilo.getParticipant().getId());
        assertEquals(dataLoader.getPhilo().getId(), karenALaPhilo.getActivite().getId());
    }

    @Test
    public void testKarenEstInscriteAuPingPong() {
        // Karen est inscrite au PingPong
        Inscription karenAuPingPong = dataLoader.getKarenAuPingPong();
        assertEquals(dataLoader.getKaren().getId(), karenAuPingPong.getParticipant().getId());
        assertEquals(dataLoader.getPingpong().getId(), karenAuPingPong.getActivite().getId());
    }
}

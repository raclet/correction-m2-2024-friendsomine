package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ActiviteServiceIntegrationTest {

    @Autowired
    private ActiviteService activiteService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private DataLoader dataLoader;

    private Activite act;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        utilisateur = new Utilisateur("unNom", "unPrenom", "ttt@tttt.fr", "F");
        act = new Activite("titre", "descriptif", utilisateur);
    }

    @Test
    public void testFindActiviteWithUnexistingId() {
        // when:  findActiviteById est appelé avec un id ne correspondant à aucun objet en base
        // then: null est retourné
        assertNull(activiteService.findActiviteById(1000L));
    }

    @Test
    public void testFindAllActiviteFromDataLoaderCardinal() {
        // given: un DataLoader initialisant la base à 7 activités
        // when: la liste des activités est récupérée
        ArrayList<Activite> activites = activiteService.findAllActivites();
        // then: il y a 7 activités dedans
        assertEquals(7, activites.size(), "Nombre d'activités présentes dans le DataLoader récupérées dans le service");
    }

    @Test
    public void testFindAllActiviteFromDataLoaderAreSortedByTitre() {
        // given: un DataLoader initialisant la base des Activite
        // when: la liste des activités est récupérée
        ArrayList<Activite> activites = activiteService.findAllActivites();
        // then: la liste est triée selon le Titre des Activites
        assertTrue((activites.get(0).getTitre()).compareTo(activites.get(1).getTitre()) < 0,
                "les éléments 0 et 1 de la liste sont bien triés");
        assertTrue((activites.get(1).getTitre()).compareTo(activites.get(2).getTitre()) < 0,
                "les éléments 1 et 2 de la liste sont bien triés");
        assertTrue((activites.get(2).getTitre()).compareTo(activites.get(3).getTitre()) < 0,
                "les éléments 2 et 3 de la liste sont bien triés");
        assertTrue((activites.get(3).getTitre()).compareTo(activites.get(4).getTitre()) < 0,
                "les éléments 3 et 4 de la liste sont bien triés");
        assertTrue((activites.get(4).getTitre()).compareTo(activites.get(5).getTitre()) < 0,
                "les éléments 4 et 5 de la liste sont bien triés");
        assertTrue((activites.get(5).getTitre()).compareTo(activites.get(6).getTitre()) < 0,
                "les éléments 5 et 6 de la liste sont bien triés");
    }

}
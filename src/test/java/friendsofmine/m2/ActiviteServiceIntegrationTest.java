package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.ActiviteService;
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

    private Activite act;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        utilisateur = new Utilisateur("unNom", "unPrenom", "ttt@tttt.fr", "F");
        act = new Activite("titre", "descriptif", utilisateur);
    }

    @Test
    public void testSavedActiviteHasId(){
        // given: une Activite non persistée act
        // then: act n'a pas d'id
        assertNull(act.getId());
        // when: act est persistée
        Activite actSaved = activiteService.saveActivite(act);
        // then: act a un id
        assertNotNull(actSaved.getId());
    }

    @Test
    public void testSaveActiviteNull(){
        // when: null est persisté via un ActiviteService
        // then: une exception IllegalArgumentException est levée
        assertThrows(IllegalArgumentException.class, () -> activiteService.saveActivite(null));
    }

    @Test
    public void testFetchedActiviteIsNotNull() {
        // given: une Activite persistée act
        Activite actSaved = activiteService.saveActivite(act);
        // when: on appelle findActiviteById avec l'id de cette Activite
        Activite fetched = activiteService.findActiviteById(actSaved.getId());
        // then: le résultat n'est pas null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedActiviteIsUnchangedForDescriptif() {
        // given: une Activite persistée act
        Activite actSaved = activiteService.saveActivite(act);
        // when: on appelle findActiviteById avec l'id de cette Activite
        Activite fetched = activiteService.findActiviteById(actSaved.getId());
        // then: l'Activite obtenue en retour a le bon id
        assertEquals(fetched.getId(), actSaved.getId());
        // then : l'Activite obtenue en retour a le bon descriptif
        assertEquals(fetched.getDescriptif(), actSaved.getDescriptif());
    }

    @Test
    public void testUpdatedActiviteIsUpdated() {
        // given: une Activite persistée act
        Activite actSaved = activiteService.saveActivite(act);

        Activite fetched = activiteService.findActiviteById(actSaved.getId());
        // when: le descriptif est modifié au niveau "objet"
        fetched.setDescriptif("Nouvelle description");
        // when: l'objet act est mis à jour en base
        Activite fetchedSaved = activiteService.saveActivite(fetched);
        // when: l'objet act est relu en base
        Activite fetchedUpdated = activiteService.findActiviteById(actSaved.getId());
        // then: le descriptif a bien été mis à jour
        assertEquals(fetchedSaved.getDescriptif(), fetchedUpdated.getDescriptif());
    }

    @Test
    public void testUpdateDoesNotCreateANewEntry() {
        // given: une Activite persistée act
        Activite actSaved = activiteService.saveActivite(act);

        long count = activiteService.countActivite();
        Activite fetched = activiteService.findActiviteById(actSaved.getId());
        // when: le descriptif est modifié au niveau "objet"
        fetched.setDescriptif("Nouvelle description");
        // when: l'objet act est mis à jour en base
        activiteService.saveActivite(fetched);
        // then: une nouvelle entrée n'a pas été créée en base
        assertEquals(count, activiteService.countActivite());
    }

    @Test
    public void testFindActiviteWithUnexistingId() {
        // when: findActiviteById est appelé avec un id ne correspondant à aucun objet en base
        // then: null est retourné
        assertNull(activiteService.findActiviteById(1000L));
    }

    @Test
    public void testSaveActiviteWithNewUtilisateur() {
        // given: une Activite non persistée act
        // when: act est persistée
        Activite actSaved = activiteService.saveActivite(act);
        // then: son responsable est persisté aussi
        assertNotNull(actSaved.getResponsable().getId());
        // then: act est ajouté à la liste des activités du responsable
        assertTrue(actSaved.getResponsable().getActivites().contains(actSaved));
    }

    @Test
    public void testSaveActiviteWithAlreadySavedUtilisateur() {
        // given: une Activite et un Utilisateur non persistés act
        // when: l'Utilisateur est persisté
        Utilisateur utilSaved = activiteService.getUtilisateurRepository().save(utilisateur);
        // when: act est persistée
        Activite actSaved = activiteService.saveActivite(act);
        // then: act a un id
        assertNotNull(actSaved.getId());
        // then: act est ajouté à la liste des activités du responsable
        assertTrue(actSaved.getResponsable().getActivites().contains(actSaved));
    }

    @Test
    public void testAnActiviteIsOnlyAddedOnceToTheResponsable() {
        long countActiviteResponsable = utilisateur.getActivites().size();
        Activite act1 = new Activite("uneActivite", "unDescriptif", utilisateur);
        Activite actSaved = activiteService.saveActivite(act1);
        assertEquals(countActiviteResponsable + 1, utilisateur.getActivites().size());
        actSaved = activiteService.saveActivite(act1);
        assertEquals(countActiviteResponsable + 1, utilisateur.getActivites().size());
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

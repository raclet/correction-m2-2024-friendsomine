package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.services.ActiviteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ActiviteServiceIntegrationTest {

    @Autowired
    private ActiviteService activiteService;

    private Activite act;

    @BeforeEach
    public void setup() {
        act = new Activite("titre", "descriptif");
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
        assertThrows(IllegalArgumentException.class, () -> { activiteService.saveActivite(null); });
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
    @Transactional
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
    @Transactional
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
        // when:  findActiviteById est appelé avec un id ne correspondant à aucun objet en base
        // then: null est retourné
        assertNull(activiteService.findActiviteById(1000L));
    }

}

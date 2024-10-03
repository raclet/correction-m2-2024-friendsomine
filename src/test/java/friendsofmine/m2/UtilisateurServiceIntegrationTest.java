package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.util.TransactionalRunner;
import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static java.time.Duration.ofMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UtilisateurServiceIntegrationTest {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ActiviteService activiteService;

    @Autowired
    private DataLoader dataLoader;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionalRunner txRunner;

    private Utilisateur util;
    private Activite act;


    @BeforeEach
    public void setup() {
        util = new Utilisateur("nom", "prenom", "toto@toto.fr", "F");
        act = new Activite("titre", "descriptif", util);
    }

    @Test
    public void testSavedUtilisateurHasId(){
        // given: un Utilisateur non persisté util
        // then: util n'a pas d'id
        assertNull(util.getId());
        // when: util est persistée
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        // then: util a id
        assertNotNull(utilSaved.getId());
    }

    @Test
    public void testSaveUtilisateurNull(){
        // when: null est persisté via un UtilisateurService
        // then: une exception IllegalArgumentException est levée
        assertThrows(IllegalArgumentException.class, () -> { utilisateurService.saveUtilisateur(null); });
    }

    @Test
    public void testFetchedUtilisateurIsNotNull() {
        // given: un Utilisateur persisté util
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        // when: on appelle findUtilisateurById avec l'id de cet Utilisateur
        Utilisateur fetched = utilisateurService.findUtilisateurById(utilSaved.getId());
        // then: le résultat n'est pas null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedUtilisateurHasGoodId() {
        // given: un Utilisateur persisté util
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        // when: on appelle findUtilisateurById avec l'id de cet Utilisateur
        Utilisateur fetched = utilisateurService.findUtilisateurById(utilSaved.getId());
        // then: l'Utilisateur obtenu en retour a le bon id
        assertEquals(utilSaved.getId(), fetched.getId());
    }

    @Test
    public void testFetchedUtilisateurIsUnchanged() {
        // given: un Utilisateur persisté util
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        // when: on appelle findUtilisateurById avec l'id de cet Utilisateur
        Utilisateur fetched = utilisateurService.findUtilisateurById(utilSaved.getId());
        // then: les attributs de l'Utilisateur obtenu en retour a les bonnes valeurs
        assertEquals(utilSaved.getNom(), fetched.getNom());
        assertEquals(utilSaved.getPrenom(), fetched.getPrenom());
        assertEquals(utilSaved.getEmail(), fetched.getEmail());
        assertEquals(utilSaved.getSexe(), fetched.getSexe());
    }

    @Test
    public void testUpdatedUtilisateurIsUpdated() {
        // given: un Utilisateur persisté util
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);

        Utilisateur fetched = utilisateurService.findUtilisateurById(utilSaved.getId());
        // when: l'email est modifié au niveau "objet"
        fetched.setEmail("tyty@tyty.fr");
        // when: l'objet util est mis à jour en base
        Utilisateur fetchedSaved = utilisateurService.saveUtilisateur(fetched);
        // when: l'objet util est relu en base
        Utilisateur fetchedUpdated = utilisateurService.findUtilisateurById(fetchedSaved.getId());
        // then: l'email a bien été mis à jour
        assertEquals(fetchedSaved.getEmail(), fetchedUpdated.getEmail());
    }

    @Test
    public void testSavedUtilisateurIsSaved() {
        long before = utilisateurService.countUtilisateur();
        // given: un nouvel Utilisateur
        // when: cet Utilisateur est persisté
        utilisateurService.saveUtilisateur(new Utilisateur("john", "john", "john@john.fr", "M"));
        // le nombre d'Utilisateur persisté a augmenté de 1
        assertEquals(before + 1, utilisateurService.countUtilisateur());
    }

    @Test
    public void testUpdateUtilisateurDoesNotCreateANewEntry() {
        // given: un Utilisateur persisté util
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        long count = utilisateurService.countUtilisateur();

        Utilisateur fetched = utilisateurService.findUtilisateurById(utilSaved.getId());
        // when: l'email est modifié au niveau "objet"
        fetched.setEmail("titi@titi.fr");
        // when: l'objet est mis à jour en base
        utilisateurService.saveUtilisateur(fetched);
        // then: une nouvelle entrée n'a pas été créée en base
        assertEquals(count, utilisateurService.countUtilisateur());
    }

    @Test
    public void testFindUtilisateurWithUnexistingId() {
        // when:  findUtilisateurById est appelé avec un id ne correspondant à aucun objet en base
        // then: null est retourné
        assertNull(utilisateurService.findUtilisateurById(1000L));
    }

    @Test
    public void testSavedActiviteHasId(){
        // given: une Activite transiente act
        // then: act n'a pas d'id
        assertNull(act.getId());
        // when: l'entité parente utilisateur est persistée
        utilisateurService.saveUtilisateur(util);
        // then: l'entité fils act a un id
        assertNotNull(act.getId());
    }

    @Test
    public void testFetchedActiviteIsNotNull() {
        // given: une Activite persistée act via l'entité parente
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        // when: on appelle findActiviteById avec l'id de cette Activite
        Activite fetched = activiteService.findActiviteById(act.getId());
        // then: le résultat n'est pas null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedActiviteIsUnchangedForDescriptif() {
        // given: une Activite persistée act via l'entité parente
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);
        // when: on appelle findActiviteById avec l'id de cette Activite
        Activite fetched = activiteService.findActiviteById(act.getId());
        // then: l'Activite obtenue en retour a le bon id
        assertEquals(fetched.getId(), act.getId());
        // then : l'Activite obtenue en retour a le bon descriptif
        assertEquals(fetched.getDescriptif(), act.getDescriptif());
    }

    @Test
    public void testUpdatedActiviteIsUpdated() {
        // given: une Activite persistée act via l'entité parente
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);

        Activite fetched = activiteService.findActiviteById(act.getId());
        // when: le descriptif est modifié au niveau "objet"
        fetched.setDescriptif("Nouvelle description");
        // when: l'entité parente est mise à jour en base
        Utilisateur fetchedSaved = utilisateurService.saveUtilisateur(util);
        // when: l'objet act est relu en base
        Activite fetchedUpdated = activiteService.findActiviteById(act.getId());
        // then: le descriptif a bien été mis à jour
        assertEquals(fetchedSaved.getActivites().iterator().next().getDescriptif(),
                fetchedUpdated.getDescriptif());
    }

    @Test
    public void testUpdateActiviteDoesNotCreateANewEntry() {
        // given: une Activite persistée act via l'entité parente
        Utilisateur utilSaved = utilisateurService.saveUtilisateur(util);

        long count = activiteService.countActivite();
        Activite fetched = activiteService.findActiviteById(act.getId());
        // when: le descriptif est modifié au niveau "objet"
        fetched.setDescriptif("Nouvelle description");
        // when: l'objet act est mis à jour en base via l'entité parente
        utilisateurService.saveUtilisateur(util);
        // then: une nouvelle entrée n'a pas été créée en base
        assertEquals(count, activiteService.countActivite());
    }

    @Test
    public void testDeleteJulian() {
        // given: une activite ping pong
        Activite pingpong = dataLoader.getPingpong();
        // given: l'utilisateur Julian
        Utilisateur julian = dataLoader.getJulian();
        // given: le nombre total d'activite
        long nbActivites = activiteService.countActivite();
        // given: le nombre total d'utilisateur
        long nbUtilisateur = utilisateurService.countUtilisateur();

        // then: l'utilisateur référence bien l'activité dans sa liste de responsabilité
        assertTrue(julian.getActivites().contains(pingpong));

        // when: l'utilisateur est supprimée
        utilisateurService.deleteUtilisateur(julian);

        // then : le nombre d'activite est inchangé
        assertEquals(nbActivites, activiteService.countActivite());
        // then : le nombre d'utilisateur a diminué de 1
        assertEquals(nbUtilisateur - 1, utilisateurService.countUtilisateur());
        // then: ping pong n'a plus de responsable
        assertNull(pingpong.getResponsable());
    }

    @Test
    public void testUtilisateurAreVersioned() {
        // given: un utilisateur fraîchement ajouté en base et positionné dans le contexte de persistance
        Utilisateur utilisateur = entityManager.merge(dataLoader.getThom());

        // when: on consulte le numéro de version de l'utilisateur
        // then: l'utilisateur a le numéro de version 0
        assertThat(utilisateur.getVersion(), is(0L));
        // when: on modifie l'utilisateur en base
        utilisateur.setEmail("new@mail.ru");
        utilisateurService.saveUtilisateur(utilisateur);
        entityManager.flush();
        // then: l'utilisateur a le numéro de version 1
        assertThat(utilisateur.getVersion(), is(1L));
    }


    @Test
    public void testOptimisticLockingOnConcurrentUtilisateurModification() {
        assertThrows(ObjectOptimisticLockingFailureException.class,
                () ->
                {
                    txRunner.doInTransaction(em -> {
                        em.persist(util);
                    });
                    txRunner.doInTransaction(em1 -> {
                        Utilisateur u1 = em1.find(Utilisateur.class, util.getId());
                        txRunner.doInTransaction(em2 -> {
                            Utilisateur u2 = em2.find(Utilisateur.class, util.getId());
                            u2.setEmail("new2@mail.ru");
                        });
                        u1.setEmail("new1@mail.ru");
                    });
                });
    }

    @Test
    public void testUtilisateurCanBeFetchedInLessThan7Seconds() {

        assertTimeout(ofMillis(7000), () -> {
            // when: les utilisateurs thom et karen sont récupérés en base
            utilisateurService.findUtilisateurByEmail("thom@rh.com");
            utilisateurService.findUtilisateurByEmail("karen@yyy.com");
            // when: on répète une demande d'accès à thom et karen
            utilisateurService.findUtilisateurByEmail("thom@rh.com");
            utilisateurService.findUtilisateurByEmail("karen@yyy.com");
            utilisateurService.findUtilisateurByEmail("thom@rh.com");
            utilisateurService.findUtilisateurByEmail("thom@rh.com");
            // then: les 6 requêtes sont exécutées en moins de 7 secondes
        });
    }

}

package friendsofmine.m2;

import friendsofmine.m2.domain.*;
import friendsofmine.m2.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class InscriptionServiceIntegrationTest {

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private UtilisateurService utilisateurService;

    private Inscription ins, ins1;
    private Utilisateur util;
    private Activite act;

    @BeforeEach
    public void setup() {
        Date date = new Date();

        util = utilisateurService.saveUtilisateur(new Utilisateur("Dupuis", "Bernard", "bernie@domain.com", "M"));
        Utilisateur resp = new Utilisateur("Dupond", "Sofia", "sof@domain.com", "F");
        act = new Activite("Chant", "Cours particulier uniquement", resp);

        resp = utilisateurService.saveUtilisateur(resp);
        ins = inscriptionService.saveInscription(new Inscription(util, act, date));
        ins1 = new Inscription(util, act, date);
    }

    @Test
    public void testSaveInscription(){
        // given: une Inscription non persistée ins1
        // then: ins1 n'a pas d'id
        assertNull(ins1.getId());
        // when: ins1 est persistée
        ins1 = inscriptionService.saveInscription(ins1);
        // then: ins1 a un id
        assertNotNull(ins1.getId());
    }

    @Test
    public void testSaveInscriptionNull(){
        // when: null est persisté via un InscriptionService
        // then: une exception IllegalArgumentException est levée
        assertThrows(IllegalArgumentException.class, () ->{
            inscriptionService.saveInscription(null);
        });
    }

    @Test
    public void testInscriptionRecupereeNotNull() {
        // when: une Inscription persistée est récupérée en base par son Id
        Inscription fetched = inscriptionService.findInscriptionById(ins.getId());
        // then: un résultat non null est récupéré
        assertNotNull(fetched);
    }

    @Test
    public void testInscriptionRecupereeInchangee() {
        // when: une Inscription persistée est récupérée en base par son Id
        Inscription fetched = inscriptionService.findInscriptionById(ins.getId());
        // then: les attributs du résultat obtenu ont leur valeur attendue
        assertEquals(fetched.getId(), ins.getId());
        assertEquals(fetched.getActivite(), ins.getActivite());
        assertEquals(fetched.getParticipant(), ins.getParticipant());
    }

    @Test
    public void testInscriptionMiseAJourIdPreserve() {
        // given: un id valide et l'inscription correspondante
        Long idAvantMiseAJour = ins.getId();
        Inscription fetched = inscriptionService.findInscriptionById(idAvantMiseAJour);
        // when: la date associée à l'inscription est modifiée
        fetched.setDateInscription(new Date());
        // when: l'inscription est à nouveau sauvée
        fetched = inscriptionService.saveInscription(fetched);
        // then: l'id n'a pas changé : une mise-à-jour de l'entrée dans la base de données a eu lieu
        assertEquals(idAvantMiseAJour, fetched.getId());
    }

    @Test
    public void testNombreDUtilisateurPersisteeApresMiseAJour() {
        // given: le nombre d'inscription en base
        long count = inscriptionService.countInscription();
        // when: une inscription récupérée en base est modifiée
        Inscription fetched = inscriptionService.findInscriptionById(ins.getId());
        fetched.setDateInscription(new Date());
        fetched = inscriptionService.saveInscription(fetched);
        // then: le nombre d'inscription en base est inchangé
        assertEquals(count, inscriptionService.countInscription());
    }

    @Test
    public void testNombreDUtilisateurPersisteeApresAjout() {
        // given: le nombre d'inscription en base
        long count = inscriptionService.countInscription();
        // when: une inscription est ajoutée en base
        ins1 = inscriptionService.saveInscription(ins1);
        // then: le nombre d'inscription en base augmente de 1
        assertEquals(count + 1, inscriptionService.countInscription());
    }

    @Test
    public void testUneInscriptionPersisteeAUneDateDInscriptionNonNull() {
        // given: une inscription sans date
        Inscription inscription = new Inscription(util, act, null);
        // when: l'inscription est persistée
        inscription = inscriptionService.saveInscription(inscription);
        // then: l'inscription a une date
        assertNotNull(inscription.getDateInscription());
    }

    @Test
    public void testUneInscriptionPersisteeAUneDateDInscriptionNonNullFixe() {
        // given: une inscription sans date
        Inscription inscription = new Inscription(util, act, null);
        // when: l'inscription est persistée
        inscription = inscriptionService.saveInscription(inscription);
        Date d = inscription.getDateInscription();
        // when: l'inscription est mise à jour
        inscription = inscriptionService.saveInscription(inscription);
        // then: la date est inchangée
        assertEquals(inscription.getDateInscription(), d);
    }

    @Test public void testDeleteInscriptionRetireDeLaBase() {
        // given: une inscription persistée
        ins1 = inscriptionService.saveInscription(ins1);
        // when: l'inscription est retirée de la base
        inscriptionService.deleteInscription(ins1.getId());
        // then: elle n'est plus trouvée en base
        assertNull(inscriptionService.findInscriptionById(ins1.getId()));
    }

    @Test public void testDeleteInscriptionEstDecomptee() {
        // given: le nombre d'inscription persistée
        long count = inscriptionService.countInscription();
        // when: l'inscription est retirée de la base
        inscriptionService.deleteInscription(ins.getId());
        // then: le nombre d'inscription diminue de 1
        assertEquals(count - 1, inscriptionService.countInscription());
    }
}

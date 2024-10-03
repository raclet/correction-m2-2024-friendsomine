package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Inscription;
import friendsofmine.m2.domain.Utilisateur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InscriptionTest {

    private static Validator validator;

    private Date date;
    private Utilisateur util, resp;
    private Activite act;

    @BeforeAll
    public static void setupContext() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setupData() {
        date = new Date();
        util = new Utilisateur("Dupuis", "Bernard", "bernie@domain.com", "M");
        resp = new Utilisateur("Dupond", "Sofia", "sof@domain.com", "F");
        act = new Activite("Chant", "Cours particulier uniquement", resp);
    }

    @Test
    public void testActiviteEtParticipantEtDateNonNull() {
        // given: une Inscription avec un utilisateur, une activité, une date valide
        // when: l'Inscription est créé
        Inscription inscription = new Inscription();
        inscription.setActivite(act);
        inscription.setParticipant(util);
        inscription.setDateInscription(date);
        // then: l'Inscription est validé par le validator
        assertTrue(validator.validate(inscription).isEmpty());
    }


    @Test
    public void testParticipantNull() {
        // given: une Inscription avec un utilisateur null
        // when: l'Inscription est créé
        Inscription inscription = new Inscription();
        inscription.setActivite(act);
        inscription.setParticipant(null);
        inscription.setDateInscription(date);
        // then: l'Inscription n'est pas validé par le validator
        assertFalse(validator.validate(inscription).isEmpty());
    }

    @Test
    public void testActiviteNull() {
        // given: une Inscription avec une activité null
        // when: l'Inscription est créé
        Inscription inscription = new Inscription();
        inscription.setActivite(null);
        inscription.setParticipant(util);
        inscription.setDateInscription(date);
        // then: l'Inscription n'est pas validé par le validator
        assertFalse(validator.validate(inscription).isEmpty());
    }

    @Test
    public void testDateInscriptionNull() {
        // given: une Inscription avec une date null
        // when: l'Inscription est créé
        Inscription inscription = new Inscription();
        inscription.setActivite(act);
        inscription.setParticipant(util);
        inscription.setDateInscription(null);
        // then: l'Inscription est validé par le validator
        assertTrue(validator.validate(inscription).isEmpty());
    }
}

package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import static org.junit.jupiter.api.Assertions.*;

import friendsofmine.m2.domain.Utilisateur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ActiviteTest {

    private static Validator validator;
    private Utilisateur utilisateur = new Utilisateur("nom", "prenom", "toto@toto.fr", "F");

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTitreNonVideEtDescrptif() {
        // given: une Activite act avec un titre et un descriptif valides
        // when: act est créé
        Activite act = new Activite("unTitre", "unDescriptif", utilisateur);
        // then: act est validé par le validator
        assertTrue(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreNonVideEtDescriptifVide() {
        // given: une Activite act avec un titre et un descriptif vide
        // when: act est créé
        Activite act = new Activite("unTitre", "", utilisateur);
        // then: act est validé par le validator
        assertTrue(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreNonVideEtDescriptifNull() {
        // given: une Activite act avec un titre et un descriptif null
        // when: act est créé
        Activite act = new Activite("unTitre", null, utilisateur);
        // then: act est validé par le validator
        assertTrue(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreVide() {
        // given: une Activite act avec un titre vide et un descriptif
        // when: act est créé
        Activite act = new Activite("", "unDescriptif", utilisateur);
        // then: act n'est pas validé par le validator
        assertFalse(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreNull() {
        // given: une Activite act avec un titre null et un descriptif
        // when: act est créé
        Activite act = new Activite(null, "unDescriptif", utilisateur);
        // then: act n'est pas validé par le validator
        assertFalse(validator.validate(act).isEmpty());
    }

    @Test
    public void testResponsableNull() {
        // given: une Activite act avec un responsable null
        // when: act est créé
        Activite act = new Activite("unTitre", "unDescriptif", null);
        // then: act est validé par le validator
        assertFalse(validator.validate(act).isEmpty());
    }

    @Test
    public void testValidActiviteIsAddedToTheResponsableListOfActitive() {
        // given: une Activite act avec un titre et un descriptif valides
        // when: act est créé
        Activite act = new Activite("unTitre", "unDescriptif", utilisateur);
        // then: act appartient à la liste des activités du responsable
        assertTrue(utilisateur.getActivites().contains(act));
    }
}

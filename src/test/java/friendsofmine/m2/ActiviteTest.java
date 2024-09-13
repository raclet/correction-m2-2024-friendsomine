package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ActiviteTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTitreNonVideEtDescriptif() {
        // given: une Activite act avec un titre et un descriptif valides
        // when: act est créé
        Activite act = new Activite("unTitre", "unDescriptif");
        // then: act est validé par le validator
        assertTrue(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreNonVideEtDescriptifVide() {
        // given: une Activite act avec un titre et un descriptif vide
        // when: act est créé
        Activite act = new Activite("unTitre", "");
        // then: act est validé par le validator
        assertTrue(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreNonVideEtDescriptifNull() {
        // given: une Activite act avec un titre et un descriptif null
        // when: act est créé
        Activite act = new Activite("unTitre", null);
        // then: act est validé par le validator
        assertTrue(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreVide() {
        // given: une Activite act avec un titre vide et un descriptif
        // when: act est créé
        Activite act = new Activite("", "unDescriptif");
        // then: act n'est pas validé par le validator
        assertFalse(validator.validate(act).isEmpty());
    }

    @Test
    public void testTitreNull() {
        // given: une Activite act avec un titre null et un descriptif
        // when: act est créé
        Activite act = new Activite(null, "unDescriptif");
        // then: act n'est pas validé par le validator
        assertFalse(validator.validate(act).isEmpty());
    }

    @Test
    public void testGetters() {
        String titre = "unTitre";
        String descriptif = "unDescriptif";
        // given: une Activite act avec un titre et un descriptif valides
        // when: act est créé
        Activite act = new Activite(titre, descriptif);
        // then: les getters retournent les valeurs attendues
        assertEquals(titre, act.getTitre());
        assertEquals(descriptif, act.getDescriptif());
    }
}

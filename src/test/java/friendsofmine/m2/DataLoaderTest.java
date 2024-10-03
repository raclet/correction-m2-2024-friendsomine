package friendsofmine.m2;

import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.services.InscriptionService;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.notNullValue;

@ExtendWith(SpringExtension.class)
public class DataLoaderTest {

    private DataLoader dataLoader;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private InscriptionService inscriptionService;

    @BeforeEach
    public void setUp() {
        dataLoader = new DataLoader(utilisateurService, inscriptionService);
    }

    @Test
    public void testDataLoaderIsAnApplicationRunner(){
        // un DataLoader peut être construit à partir d'un ActiviteService et d'un UtilisateurService
        assertThat(dataLoader, is(notNullValue()));
        // un DataLoader est un ApplicationRunner
        assertTrue(dataLoader instanceof ApplicationRunner);
    }

    @Test
    public void testInitMethodCalls() {
        DataLoader spy = spy(dataLoader);
        // when: la méthode run est appelée
        spy.run(null);
        // then: la méthode initUtilisateurs() qui crée les objets utilisateurs
        verify(spy).initUtilisateurs();
        // then: la méthode initActivites() qui crée les objets activités
        verify(spy).initActivites();
        // then: la méthode initInscription() qui crée les objets inscriptions
        verify(spy).initInscriptions();
        // then: la méthode saveUtilisateursAndActivitesAndInscriptions() qui insère en base les objets utilisateurs, activités et inscriptions
        verify(spy).saveUtilisateursAndActivitesAndInscriptions();

    }
}

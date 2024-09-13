package friendsofmine.m2;

import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.repositories.UtilisateurRepository;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class UtilisateurServiceTest {

    private UtilisateurService utilisateurService;

    @MockBean
    private UtilisateurRepository utilisateurRepository;

    @BeforeEach
    public void setup() {
        utilisateurService = new UtilisateurService();
        utilisateurService.setUtilisateurRepository(utilisateurRepository);
    }

    @Test
    public void testTypeRepository() {
        // le Repository associé à un UtilisateurService est de type CrudRepository
        assertThat(utilisateurService.getUtilisateurRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    public void testFindByIdFromCrudRepositoryIsInvokedWhenUtilisateurIsFoundById() {
        // given: un UtilisateurService
        // when: la méthode findUtilisateurById est invoquée
        utilisateurService.findUtilisateurById(0L);
        // then: la méthode findById du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).findById(0L);
    }

    @Test
    public void testSaveFromCrudRepositoryIsInvokedWhenUtilisateurIsSaved() {
        // given: un UtilisateurService et un Utilisateur
        Utilisateur util = new Utilisateur("Hammond", "Missy", "missy@ts.com", "F");
        // when: la méthode saveUtilisateur est invoquée
        utilisateurService.saveUtilisateur(util);
        // then: la méthode save du Repository associé est invoquée
        verify(utilisateurService.getUtilisateurRepository()).save(util);
    }
}

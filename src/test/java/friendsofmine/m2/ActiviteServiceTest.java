package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.repositories.ActiviteRepository;
import friendsofmine.m2.repositories.UtilisateurRepository;
import friendsofmine.m2.services.ActiviteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ActiviteServiceTest {

    private ActiviteService activiteService;

    @MockBean
    private ActiviteRepository activiteRepository;

    @MockBean
    private UtilisateurRepository utilisateurRepository;

    @MockBean
    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        activiteService = new ActiviteService();
        activiteService.setActiviteRepository(activiteRepository);
        activiteService.setUtilisateurRepository(utilisateurRepository);
    }

    @Test
    public void testTypeRepository() {
        // le Repository associé à un ActiviteService est de type CrudRepository
        assertThat(activiteService.getActiviteRepository(), instanceOf(CrudRepository.class));
    }

    @Test
    public void testSaveFromCrudRepositoryIsInvokedWhenActiviteIsSaved() {
        // given: un ActiviteService et une Activite
        Activite activite = new Activite("Truc", "Description du truc", utilisateur);
        when(activiteService.getUtilisateurRepository().save(utilisateur)).thenReturn(utilisateur);
        when(activiteService.getActiviteRepository().save(activite)).thenReturn(activite);
        // when: la méthode saveActivite est invoquée
        activiteService.saveActivite(activite);
        // then: la méthode save de l'UtilisateurRepository associé est invoquée (pas de cascade !!!)
        verify(activiteService.getUtilisateurRepository()).save(utilisateur);
        // then: la méthode save de l'ActiviteRepository associé est invoquée
        verify(activiteService.getActiviteRepository()).save(activite);
    }

    @Test
    public void testFindByIdFromCrudRepositoryIsInvokedWhenActiviteIsFoundById() {
        // given: un ActiviteService
        // when: la méthode findActiviteById est invoquée
        activiteService.findActiviteById(0L);
        // then: la méthode findById du Repository associé est invoquée
        verify(activiteService.getActiviteRepository()).findById(0L);
    }

    @Test
    public void testFindAllFromCrudRepositoryIsInvokedWhenFindAllActivite() {
        // given: un ActiviteService
        // when: la méthode findAllActivite est invoquée
        activiteService.findAllActivites();
        // then: la méthode findAll du Repository associé est invoquée
        verify(activiteService.getActiviteRepository()).findAll();
    }

}

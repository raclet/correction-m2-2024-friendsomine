package friendsofmine.m2;

import friendsofmine.m2.services.ActiviteService;
import friendsofmine.m2.controllers.ActiviteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ActiviteControllerTest {

    @MockBean
    private ActiviteService activiteService;

    private ActiviteController activiteController;

    @BeforeEach
    public void setUp() {
        activiteController = new ActiviteController();
        activiteController.setActiviteService(activiteService);
    }

    @Test
    public void testControllerDelegationToService() {
        // when: on récupère dans le contrôleur la liste des activités
        activiteController.findAllActivitesWithResponsable();

        // then: la requête est traitée par le service correspondant
        verify(activiteService).findAllActivites();
    }


}

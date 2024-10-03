package friendsofmine.m2;

import friendsofmine.m2.controllers.InscriptionController;
import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Inscription;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InscriptionControllerTest {

    private InscriptionController inscriptionController;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private ActiviteService activiteService;

    @MockBean
    private InscriptionService inscriptionService;

    @MockBean
    private Utilisateur utilisateur;

    @MockBean
    private Activite activite;

    @MockBean
    private Inscription inscription;

    @BeforeEach
    public void setUp() throws Exception {
        inscriptionController = new InscriptionController();
        inscriptionController.setActiviteService(activiteService);
        inscriptionController.setUtilisateurService(utilisateurService);
        inscriptionController.setInscriptionService(inscriptionService);

        when(utilisateurService.findUtilisateurById(1L)).thenReturn(utilisateur);
        when(activiteService.findActiviteById(2L)).thenReturn(activite);
        when(inscriptionService.findInscriptionById(1L)).thenReturn(inscription);
    }

    @Test
    public void testAddInscription() {
        // when: add request is triggered
        inscriptionController.addInscription(1L,2L);

        // then: several collaborations are triggered
        verify(utilisateurService).findUtilisateurById(1L);
        verify(activiteService).findActiviteById(2L);
        verify(inscriptionService).saveInscription(any(Inscription.class));
    }

    @Test
    public void testRemoveInscription() {
        // when: delete request is triggered
        inscriptionController.deleteInscription(1L);

        // then: a delete collaboration is triggered
        verify(inscriptionService).deleteInscription(1L);
    }

}

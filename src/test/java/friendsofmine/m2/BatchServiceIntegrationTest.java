package friendsofmine.m2;

import friendsofmine.m2.services.BatchService;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BatchServiceIntegrationTest {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private BatchService batchService;

    @Test
    public void testBatchAddWithFailure() {
        // given: un nombre "count" d'utilisateur en base
        long count = utilisateurService.countUtilisateur();

        // when: on essaie d'ajouter un lot d'utilisateur de manière atomique
        // when: l'ajout du lot échoue volontairement dans saveUsAll
        try {
            batchService.saveUsAll();
        } catch(Exception ignored) {

        }

        // then: aucun utilisateur du lot n'a été ajouté en base
        assertEquals(count , utilisateurService.countUtilisateur());
    }
}

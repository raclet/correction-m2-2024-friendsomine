package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.domain.Inscription;
import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.InscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class InscriptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private DataLoader dataLoader;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @Test
    public void testShowInscriptionExistante() throws Exception {

        Inscription thomOnPingpong = dataLoader.getThomAuPingPong();

        // when: une requête est émise pour obtenir le détail d'une inscription
        mockMvc.perform(get("/api/inscription/" + thomOnPingpong.getId()))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                // then: la réponse est en utf8
                .andExpect(content().contentType(contentType))
                // then: la réponse est au format JSON et comporte les infos sur l'inscription
                .andExpect(jsonPath("$.id", is(thomOnPingpong.getId().intValue())))
                .andExpect(jsonPath("$.participant.nom", is(thomOnPingpong.getParticipant().getNom())))
                .andExpect(jsonPath("$.activite.titre", is(thomOnPingpong.getActivite().getTitre())));
    }

    @Test
    public void testShowInscriptionInexistante() throws Exception {

        // assert: l'id suivant ne correspond à aucune inscription en base
        Long idInexistant = 12345L;
        assertNull(inscriptionService.findInscriptionById(idInexistant));

        // when: une requête est émise pour obtenir le détail d'une inscription inexistante
        mockMvc.perform(get("/api/inscription/" + idInexistant))
                // then: la réponse a le status 404
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAjoutInscription() throws Exception {

        Utilisateur ed = dataLoader.getEd();
        Activite pingpong = dataLoader.getPingpong();

        // when: une requête est émise pour créer une nouvelle inscription de l'utilisateur précédent à l'activite précédente
        mockMvc.perform(post("/api/inscription?utilisateur_id=" + ed.getId() + "&activite_id=" + pingpong.getId()))
                // then: la réponse a le status 201 (CREATED)
                .andExpect(status().isCreated())
                // then: la réponse est en utf8
                .andExpect(content().contentType(contentType))
                // then: la réponse est au format JSON et comporte les infos sur l'inscription
                .andExpect(jsonPath("$.participant.nom", is(ed.getNom())))
                .andExpect(jsonPath("$.activite.titre", is(pingpong.getTitre())));
    }

    @Test
    public void testDeleteInscription() throws Exception {

        Inscription thomOnPingpong = dataLoader.getThomAuPingPong();
        Long count = inscriptionService.countInscription();

        // when: une requête de suppression est émise
        mockMvc.perform(delete("/api/inscription/" + thomOnPingpong.getId()))
                // then: la supression est effectuée avec succès
                .andExpect(status().isOk());

        // then: le nombre d'inscription a diminué de 1
        assertEquals(count - 1, inscriptionService.countInscription());
    }


}

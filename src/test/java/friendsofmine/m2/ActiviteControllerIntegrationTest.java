package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ActiviteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoader dataLoader;

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    private String jsonResult;


    @Test
    public void testFindAllActivitesWithResponsable() throws Exception {

        // given: une activité "ping-pong" en base
        Activite testedActivite = dataLoader.getPingpong();

        // when: l'utilisateur émet une requête pour obtenir la liste des activités
        mockMvc.perform(get("/activitesWithResponsable"))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                // then: la réponse est au format JSON et utf8
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> jsonResult = mvcResult.getResponse().getContentAsString());

        // then: le résultat obtenu contient le titre d'une activité persistée
        assertThat(jsonResult, containsString(testedActivite.getTitre()));
        // then: le résultat obtenu contient le descriptif d'une activité persistée
        assertThat(jsonResult, containsString(testedActivite.getDescriptif()));

    }

}

package friendsofmine.m2;

import friendsofmine.m2.domain.Inscription;
import friendsofmine.m2.services.InscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class EqualsInOrmTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private InscriptionService inscriptionService;

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;
    private String fooResourceUrl;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        fooResourceUrl = "http://localhost:" + port + "/api/inscription";
    }

    @Test
    public void testEgaliteDInscriptionPersisteeViaId() throws Exception {

        Inscription edAuJogging = dataLoader.getEdAuJogging();

        // when: une requête pour récupérer les infos d'une inscription est émise à partir d'un id valide
        Inscription response
                = restTemplate.getForObject(fooResourceUrl + "/" + edAuJogging.getId(), Inscription.class);
        // then: le participant de l'inscription obtenue par la requête a le même id que l'id de départ
        assertThat(response.getParticipant().getId(), is(edAuJogging.getParticipant().getId()));
    }

    @Test
    public void testEgaliteDInscriptionPersisteeViaEquals() throws Exception {

        Inscription edAuJogging = dataLoader.getEdAuJogging();

        // when: une requête pour récupérer les infos d'une inscription est émise à partir d'un id valide
        Inscription response
                = restTemplate.getForObject(fooResourceUrl + "/" + edAuJogging.getId(), Inscription.class);
        // then: le participant de l'inscription obtenue par la requête est le même que celui de départ
        assertThat(response.getParticipant(), is(edAuJogging.getParticipant()));

    }

}

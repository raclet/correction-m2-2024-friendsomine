package friendsofmine.m2.repositories;
import friendsofmine.m2.domain.Inscription;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface InscriptionRepository extends CrudRepository<Inscription, Long> {

    public ArrayList<Inscription> findByParticipantNomOrActiviteTitreAllIgnoreCase(String nom, String titre);

}
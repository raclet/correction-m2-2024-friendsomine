package friendsofmine.m2.repositories;
import friendsofmine.m2.domain.Inscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InscriptionRepository extends PagingAndSortingRepository<Inscription, Long>, CrudRepository<Inscription, Long> {
    public Page<Inscription> findByParticipantNomOrActiviteTitreAllIgnoreCase(String nom, String titre, Pageable pageable);
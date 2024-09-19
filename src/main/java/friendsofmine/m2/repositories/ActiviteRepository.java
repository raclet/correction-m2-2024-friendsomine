package friendsofmine.m2.repositories;

import friendsofmine.m2.domain.Activite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiviteRepository extends CrudRepository<Activite, Long> {

    @Query("select activite from Activite activite join fetch activite.responsable order by activite.titre asc")
    public Iterable<Activite> findAll();

}
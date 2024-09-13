package friendsofmine.m2.services;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.repositories.ActiviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository ;

    public Activite saveActivite(Activite activite) {
        if (activite == null)
            throw new IllegalArgumentException();
        return  activiteRepository.save(activite) ;
    }

    public Activite findActiviteById(Long id) {
        return activiteRepository.findById(id).orElse(null);
    }

    public long countActivite() {
        return activiteRepository.count();
    }

    public ActiviteRepository getActiviteRepository() {
        return activiteRepository;
    }

    public void setActiviteRepository(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }

}

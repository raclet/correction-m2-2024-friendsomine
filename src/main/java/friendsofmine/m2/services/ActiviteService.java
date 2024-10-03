package friendsofmine.m2.services;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.repositories.ActiviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository ;

    public Activite findActiviteById(Long id) {
        return activiteRepository.findById(id).orElse(null);
    }

    public ArrayList<Activite> findAllActivites() {
        Iterable<Activite> activites = activiteRepository.findAll();

        ArrayList<Activite> activiteList = new ArrayList<>();
        activites.forEach(activiteList::add);

        return activiteList;
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

package check_in42.backend.presentation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PresentationRepository {

    private final EntityManager em;

    public void save(Presentation presentation) {
        em.persist(presentation);
    }

    public void delete(Presentation presentation) {
        em.remove(presentation);
    }

    public Presentation findOne(Long id) {
        return em.find(Presentation.class, id);
    }
}

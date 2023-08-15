package check_in42.backend.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void delete(User user) {
        em.remove(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public User findByName(String intraId) {
        return em.createQuery("select u from User u where u.intraId = intraId", User.class)
                .setParameter("intraId", intraId)
                .getSingleResult();
    }
}

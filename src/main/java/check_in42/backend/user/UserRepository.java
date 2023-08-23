package check_in42.backend.user;

import check_in42.backend.allException.CustomException;
import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.user.exception.UserRunTimeException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    public Optional<User> findByName(String intraId) throws CustomException {
        try {
            User user = em.createQuery("select u from User u where u.intraId = :intraId", User.class)
                    .setParameter("intraId", intraId)
                    .getSingleResult();
            return Optional.of(user);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByRefreshToken(String refreshToken) {
        try {
            User user = em.createQuery("select u from User u where u.refreshToken = :refreshToken", User.class)
                    .setParameter("refreshToken", refreshToken)
                    .getSingleResult();
            return Optional.of(user);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}

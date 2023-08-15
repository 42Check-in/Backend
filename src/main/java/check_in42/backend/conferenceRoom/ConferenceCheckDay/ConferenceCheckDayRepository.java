package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ConferenceCheckDayRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ConferenceCheckDay conferenceCheckDay) {
        em.persist(conferenceCheckDay);
    }

    public ConferenceCheckDay findOne(Long id) {
        return em.find(ConferenceCheckDay.class, id);
    }

    public List<ConferenceCheckDay> findAll() {
        return em.createQuery("select c from ConferenceCheckDay c", ConferenceCheckDay.class)
                .getResultList();
    }

    public ConferenceCheckDay findByDate(Long year, Long month) {
        return em.createQuery("select c from ConferenceCheckDay c where c.year = :year and c.month = :month", ConferenceCheckDay.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .getSingleResult();
    }
}

package check_in42.backend.conferenceRoom.ConferenceRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ConferenceRoomRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(ConferenceRoom conferenceRoom) {
        em.persist(conferenceRoom);
    }

    public ConferenceRoom findOne(Long id) {
        return em.find(ConferenceRoom.class, id);
    }

    public List<ConferenceRoom> findAll() {
        return em.createQuery("select c from ConferenceRoom c", ConferenceRoom.class)
                .getResultList();
    }

    public List<ConferenceRoom> findByDay(Long day) {
        return em.createQuery("select c from ConferenceRoom as c " +
                        "where date_format(date, '%d') = :day")
                .setParameter("day", day)
                .getResultList();
    }
}

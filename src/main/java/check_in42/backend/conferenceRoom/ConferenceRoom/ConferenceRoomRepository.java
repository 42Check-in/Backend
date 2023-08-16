package check_in42.backend.conferenceRoom.ConferenceRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public Long getSumReservationCountByDate(String date) {
        return em.createQuery("select sum(c.reservationCount) from ConferenceRoom c where date = :date", Long.class)
                .setParameter("date", date)
                .getSingleResult();
    }

    public List<ConferenceRoom> findByDay(Long day) {
        return em.createQuery("select c from ConferenceRoom c " +
                        "where date_format(date, '%d') = :day", ConferenceRoom.class)
                .setParameter("day", day)
                .getResultList();
    }

    public List<ConferenceRoom> findByDateAndSamePlace(String date, Long reqPlaceInfoBit) {
        return em.createQuery("select c from ConferenceRoom c " +
                        "where date = :date and (reservationInfo & :reqPlaceInfoBit = :reqPlaceInfoBit)", ConferenceRoom.class)
                .setParameter("date", date)
                .setParameter("reqPlaceInfoBit", reqPlaceInfoBit)
                .getResultList();
    }
}

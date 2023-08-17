package check_in42.backend.presentation;

import check_in42.backend.equipments.Equipment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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

    public List<Presentation> findAll() {
        return em.createQuery("select p from Presentation p", Presentation.class)
                .getResultList();
    }

    public Presentation findOne(Long id) {
        return em.find(Presentation.class, id);
    }

    public List<Presentation> findOneMonth(String month) {
        YearMonth mon = YearMonth.parse(month);

        LocalDate start = mon.atDay(1);
        LocalDate end = mon.atEndOfMonth();

        return em.createQuery("SELECT p FROM Presentation p WHERE p.date BETWEEN :start AND :end", Presentation.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<Presentation> findDataBeforeDay(int day) {
        LocalDate minusDay = LocalDate.now().minusDays(day);

        return em.createQuery("select p from Presentation p where p.agreeDate <= :minusDay " +
                        "order by p.agreeDate desc", Presentation.class)
                .setParameter("minusDay", minusDay)
                .getResultList();
    }
}

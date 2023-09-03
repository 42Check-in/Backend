package check_in42.backend.presentation;

import check_in42.backend.allException.CustomException;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.utils.PresentationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PresentationRepository {

    private final EntityManager em;

    public void save(Presentation presentation) {
        em.persist(presentation);
    }

    public void delete(Long formId) {
        Presentation presentation = findOne(formId).get();
        em.remove(presentation);
    }

    public List<Presentation> findAllNotApprovalFirst(Integer categoryType) {
        Query query = em.createQuery(
                "SELECT v FROM Visitors v " +
                        "LEFT JOIN Notice n ON v.id = n.formId AND n.category = :categoryType " +
                        "ORDER BY " +
                        "CASE WHEN n.formId IS NULL THEN 1 ELSE 0 END DESC, " +
                        "CASE WHEN n.formId IS NULL THEN v.id END DESC, " +
                        "CASE WHEN n.formId IS NOT NULL THEN v.id END DESC"
        );
        query.setParameter("categoryType", categoryType);
        return query.getResultList();
    }

    public List<Presentation> findAll() {
        return em.createQuery("select p from Presentation p", Presentation.class)
                .getResultList();
    }

    public Optional<Presentation> findOne(Long id) throws CustomException {
        try {
            Presentation presentation = em.find(Presentation.class, id);
            return Optional.of(presentation);
        }
        catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public List<Presentation> findOneMonth(String month) {
        LocalDate res = LocalDate.parse(month);

        LocalDate start = res.withDayOfMonth(1);
        LocalDate end = res.withDayOfMonth(res.lengthOfMonth());

        return em.createQuery("SELECT p FROM Presentation p WHERE p.date BETWEEN :start AND :end", Presentation.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<Presentation> findDataBeforeDay(int day) {
        LocalDate minusDay = LocalDate.now().minusDays(day);

        return em.createQuery("select p from Presentation p where p.approval <= :minusDay " +
                        "order by p.approval desc", Presentation.class)
                .setParameter("minusDay", minusDay)
                .getResultList();
    }

    public List<Presentation> findAllDESC() {
        return em.createQuery("select p from Presentation p order by p.id desc", Presentation.class)
                  .getResultList();
  }
    public List<Presentation> findByNoticeFalse() {
        return em.createQuery("select p from Presentation p where not p.notice", Presentation.class)
                .getResultList();
    }

    public List<Presentation> findByDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);

        return em.createQuery("select p from Presentation p where p.date = :date", Presentation.class)
                .setParameter("date", date)
                .getResultList();
    }

    public void setNextPresentation(LocalDate date) {
        List<Presentation> dateForms = findByDate(date.toString());
        log.info("--------dateForms size" + dateForms.size());
        if (dateForms.size() == 1) {
            return;
        }
        Presentation nextForm = dateForms.get(1);
        nextForm.setStatus(PresentationStatus.PENDING.ordinal());
    }
}

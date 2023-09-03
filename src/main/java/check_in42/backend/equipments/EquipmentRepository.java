package check_in42.backend.equipments;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EquipmentRepository {

    private final EntityManager em;


    public void save(Equipment equipment) {
        em.persist(equipment);
    }

    public Optional<Equipment> findOne(Long id) {

        try {
            final Equipment equipment = em.find(Equipment.class, id);
            return Optional.of(equipment);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public List<Equipment> findAllNotApprovalFirst(Integer categoryType) {
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

    public List<Equipment> findAll() {
        return em.createQuery("select e from Equipment e", Equipment.class)
                .getResultList();
    }

    public void delete(Equipment equipment) {
        em.remove(equipment);
    }

    public List<Equipment> findDataBeforeDay(int day) {
        LocalDate minusDay = LocalDate.now().minusDays(day);

        return em.createQuery("select e from Equipment e where e.approval <= :minusDay " +
                        "order by e.approval desc", Equipment.class)
                .setParameter("minusDay", minusDay)
                .getResultList();
    }
  
    public List<Equipment> findAllDESC() {
        return em.createQuery("select e from Equipment e order by e.id desc", Equipment.class)
               .getResultList();
    }
    public List<Equipment> findByNoticeFalse() {
        return em.createQuery("select e from Equipment e where not e.notice", Equipment.class)
                .getResultList();
    }
}

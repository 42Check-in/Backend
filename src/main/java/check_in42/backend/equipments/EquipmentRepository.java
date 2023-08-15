package check_in42.backend.equipments;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EquipmentRepository {

    private final EntityManager em;


    public void save(Equipment equipment) {
        em.persist(equipment);
    }

    public Equipment findOne(Long id) {
       return em.find(Equipment.class, id);
    }

    public List<Equipment> findAll() {
        return em.createQuery("select e from Equipment e", Equipment.class)
                .getResultList();
    }

    public void delete(Equipment equipment) {
        em.remove(equipment);
    }
}

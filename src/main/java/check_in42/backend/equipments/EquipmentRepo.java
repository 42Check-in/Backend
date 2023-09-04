package check_in42.backend.equipments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepo extends JpaRepository<Equipment, Long> {

    Page<Equipment> findByApprovalIsNull(Pageable pageable);
    Page<Equipment> findByApprovalIsNotNull(Pageable pageable);
}

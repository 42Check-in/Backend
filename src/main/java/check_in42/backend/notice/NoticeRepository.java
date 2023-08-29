package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    private final EntityManager entityManager;

    public List<NoticeDTO> getNotice(Long userId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM (" +
                        "SELECT 'visitors' AS category, id AS form_id, approval, notice FROM visitors " +
                        "WHERE user_id = :userId " +
                        "UNION " +
                        "SELECT 'equipment' AS category, id AS form_id, approval, notice FROM equipment " +
                        "WHERE user_id = :userId " +
                        "UNION " +
                        "SELECT 'presentation' AS category, id AS form_id, approval, notice FROM presentation " +
                        "WHERE user_id = :userId" +
                        ") AS notice " +
                        "WHERE approval IS NOT NULL " +
                        "AND approval BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY) " +
                        "ORDER BY approval DESC", NoticeDTO.class
        );
        query.setParameter("userId", userId);

        List<NoticeDTO> results = query.getResultList();
        return results;
    }
}

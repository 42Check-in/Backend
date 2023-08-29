package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import java.util.List;

public class NoticeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<NoticeDTO> getNotice(Long userId) {
        String nativeQuery = "SELECT 'visitors' AS category, id AS form_id, approval, notice FROM visitors " +
                "WHERE user_id = :userId " +
                "UNION " +
                "SELECT 'equipment' AS category, id AS form_id, approval, notice FROM equipment " +
                "WHERE user_id = :userId " +
                "UNION " +
                "SELECT 'presentation' AS category, id AS form_id, approval, notice FROM presentation " +
                "WHERE user_id = :userId";

        Query query = entityManager.createNativeQuery(nativeQuery)
                .setParameter("userId", userId)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(new AliasToBeanResultTransformer(NoticeDTO.class));

        List<NoticeDTO> results = query.getResultList();
        return results;
    }
}

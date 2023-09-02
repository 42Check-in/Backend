package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NoticeRepository {

    final EntityManager em;

    public List<Notice> getNotice(Long userId) {
        final String jpql = "SELECT " +
                "0 as category, id as form_id, approval, notice " +
                "FROM visitors " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "UNION " +
                "SELECT " +
                "1 as category, id as form_id, approval, notice " +
                "FROM equipment " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "UNION " +
                "SELECT " +
                "2 as category, id as form_id, approval, notice " +
                "FROM presentation " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "ORDER BY approval DESC";
        return em.createNativeQuery(jpql, NoticeDTO.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}

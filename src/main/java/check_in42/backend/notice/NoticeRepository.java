package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NoticeRepository {
    private final EntityManager em;

    public List<NoticeDTO> getNotice(@Param("userId") Long userId) {
        String jpql = "SELECT " +
                "   0 as category, id as formId, approval, notice " +
                "FROM visitors " +
                "WHERE user_id = :userId " +
//                "AND notice IS FALSE " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "UNION " +
                "SELECT " +
                "   1 as category, id as formId, approval, notice " +
                "FROM equipment " +
                "WHERE user_id = :userId " +
//                "AND notice IS FALSE " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "UNION " +
                "SELECT " +
                "   2 as category, id as formId, approval, notice " +
                "FROM presentation " +
                "WHERE user_id = :userId " +
//                "AND notice IS FALSE " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "ORDER BY approval DESC";
        final Query query = em.createNativeQuery(jpql)
                .setParameter("userId", userId);
        List<Object []> list = query.getResultList();
        final List<NoticeDTO> noticeDTOList = list.stream()
                .map(row -> NoticeDTO.create((Long) row[0], (Long) row[1],
                        ((java.sql.Date) row[2]).toLocalDate(), (boolean) row[3]))
                .collect(Collectors.toList());
        return noticeDTOList;
    }
}

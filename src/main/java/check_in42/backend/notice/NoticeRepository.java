package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.notice.utils.NoticeResponse;
import check_in42.backend.visitors.Visitors;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NoticeRepository {
    private final EntityManager em;

    public NoticeResponse getNotice(@Param("userId") Long userId) {
        String jpql = "SELECT " +
                "   0 as category, id as formId, approval, notice " +
                "FROM visitors " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "UNION " +
                "SELECT " +
                "   1 as category, id as formId, approval, notice " +
                "FROM equipment " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "UNION " +
                "SELECT " +
                "   2 as category, id as formId, approval, notice " +
                "FROM presentation " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
                "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
                "ORDER BY approval DESC";
        final Query query = em.createNativeQuery(jpql)
                .setParameter("userId", userId);
        final List<Object []> list = query.getResultList();
        final List<NoticeDTO> noticeDTOList = new ArrayList<>();
        int num = 0;
        for (Object[] row : list) {
            NoticeDTO noticeDTO = NoticeDTO.create((Long) row[0], (Long) row[1],
                    ((Date) row[2]).toLocalDate(), (boolean) row[3]);
            noticeDTOList.add(noticeDTO);
            if ((boolean) row[3] == false) {
                num += 1;
            }
        }
//        List<NoticeDTO> sorted = noticeDTOList.stream()
//                .sorted(Comparator.comparing(NoticeDTO::getDate, Comparator.nullsFirst(Comparator.reverseOrder())))
//                .toList();
        final NoticeResponse noticeResponse = NoticeResponse.create(noticeDTOList, num);
        return noticeResponse;
    }
}

package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.notice.utils.NoticeResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
//                "AND approval BETWEEN CURRENT_TIMESTAMP AND CURRENT_TIMESTAMP + 3 " +
                "UNION " +
                "SELECT " +
                "   1 as category, id as formId, approval, notice " +
                "FROM equipment " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
//                "AND approval BETWEEN CURRENT_TIMESTAMP AND CURRENT_TIMESTAMP + 3 " +
                "UNION " +
                "SELECT " +
                "   2 as category, id as formId, approval, notice " +
                "FROM presentation " +
                "WHERE user_id = :userId " +
                "AND approval IS NOT NULL " +
//                "AND approval BETWEEN CURRENT_TIMESTAMP AND CURRENT_TIMESTAMP + 3 " +
                "ORDER BY approval DESC";
        final Query query = em.createNativeQuery(jpql)
                .setParameter("userId", userId);
        final List<Object []> list = query.getResultList();
        log.info("native size : " + list.size());
        final List<NoticeDTO> noticeDTOList = new ArrayList<>();
        int num = 0;
        for (Object[] row : list) {
            NoticeDTO noticeDTO = NoticeDTO.create((Long) row[0], (Long) row[1],
                    ((java.sql.Timestamp) row[2]).toLocalDateTime(), (boolean) row[3]);
            noticeDTOList.add(noticeDTO);
            if ((boolean) row[3] == false) {
                num += 1;
            }
        }
//        List<NoticeDTO> sorted = noticeDTOList.stream()
//                .sorted(Comparator.comparing(NoticeDTO::getDate, Comparator.nullsFirst(Comparator.reverseOrder())))
//                .toList();
        log.info("" + noticeDTOList.size());
        final NoticeResponse noticeResponse = NoticeResponse.create(noticeDTOList, num);
        return noticeResponse;
    }
}

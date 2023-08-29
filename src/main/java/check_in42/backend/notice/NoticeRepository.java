package check_in42.backend.notice;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.visitors.Visitors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NoticeRepository {
    private final EntityManager em;
    private final UserRepository userRepository;
//    @Query(value = "SELECT " +
//            "   0 as category, id as formId, approval, notice " +
//            "FROM visitor " +
//            "WHERE user_id = :userId " +
//            "AND approval IS NOT NULL " +
//            "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
//            "UNION " +
//            "SELECT " +
//            "   1 as category, id as formId, approval, notice " +
//            "FROM equipment " +
//            "WHERE user_id = :userId " +
//            "AND approval IS NOT NULL " +
//            "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
//            "UNION " +
//            "SELECT " +
//            "   2 as category, id as formId, approval, notice " +
//            "FROM presentation " +
//            "WHERE user_id = :userId " +
//            "AND approval IS NOT NULL " +
//            "AND approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 " +
//            "ORDER BY approval DESC", nativeQuery = true)
    public List<NoticeDTO> getNotice(@Param("userId") Long userId) {
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
        Query query = em.createNativeQuery(jpql)
                .setParameter("userId", userId);
        log.info("tlqkfdk 들어옴?????????????????/");
        List<Object []> list = query.getResultList();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (Object[] row : list) {
            Long category = (Long) row[0];
            log.info("cata");
            Long formId = (Long) row[1];
            log.info("formId");
            LocalDate approval = ((java.sql.Date) row[2]).toLocalDate(); // 적절한 변환을 사용하여 LocalDateTime으로 변환
            boolean notice = (boolean) row[3];

            NoticeDTO noticeDTO = NoticeDTO.builder()
                    .category(category)
                    .formId(formId)
                    .date(approval)
                    .notice(notice)
                    .build();
            noticeDTOList.add(noticeDTO);
        }
        return noticeDTOList;
    }
}

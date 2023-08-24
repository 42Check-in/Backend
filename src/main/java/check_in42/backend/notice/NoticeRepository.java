package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeRepository {

    private final EntityManager em;

//    @Query(value =
//        "select * from (" +
//            "select 'visitor' as category, id as form_id, approval, notice from visitor " +
//            "where user_id = :userId " +
//            "union " +
//            "select 'equipment' as category, id as form_id, approval, notice from equipment " +
//            "where user_id = :userId " +
//            "union " +
//            "select 'presentation' as category, id as form_id, approval, notice from presentation " +
//            "where user_id = :userId" +
//        ") as notice " +
//        "where approval is not null " +
//            "and approval between now() and date_add(now(), interval 3 day) " +
//        "order by approval desc"
//    , nativeQuery = true)
//    List<NoticeDTO> getNotice(@Param("userId") Long user_id);

    public List<NoticeDTO> getNotice(Long id) {
        String jpql = "SELECT NEW check_in42.backend.notice.utils.NoticeDTO(" +
                "CASE " +
                "   WHEN v.id = :id THEN 0 " +
                "   WHEN e.id = :id THEN 1 " +
                "   WHEN p.id = :id THEN 2 " +
                "END, " +
                "CASE " +
                "   WHEN v.id = :id THEN v.id " +
                "   WHEN e.id = :id THEN e.id " +
                "   WHEN p.id = :id THEN p.id " +
                "END, " +
                "CASE " +
                "   WHEN v.id = :id THEN v.approval " +
                "   WHEN e.id = :id THEN e.approval " +
                "   WHEN p.id = :id THEN p.approval " +
                "END, " +
                "CASE " +
                "   WHEN v.id = :id THEN v.notice " +
                "   WHEN e.id = :id THEN e.notice " +
                "   WHEN p.id = :id THEN p.notice " +
                "END )" +
                "FROM Visitors v, Equipment e, Presentation p " +
                "WHERE (v.approval IS NOT NULL OR e.approval IS NOT NULL OR p.approval IS NOT NULL) " +
                "AND (v.approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 OR e.approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3 OR p.approval BETWEEN CURRENT_DATE AND CURRENT_DATE + 3) " +
                "ORDER BY " +
                "CASE " +
                "   WHEN v.id = :id THEN v.approval " +
                "   WHEN e.id = :id THEN e.approval " +
                "   WHEN p.id = :id THEN p.approval " +
                "END DESC";

        return em.createQuery(jpql, NoticeDTO.class)
                .setParameter("id", id)
                .getResultList();
    }
}

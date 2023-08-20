package check_in42.backend.notice;

import check_in42.backend.equipments.EquipmentRepository;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.PresentationRepository;
import check_in42.backend.user.User;
import check_in42.backend.visitors.VisitorsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
//            "where id = :id " +
//            "union " +
//            "select 'equipment' as category, id as form_id, approval, notice from equipment " +
//            "where id = :id " +
//            "union " +
//            "select 'presentation' as category, id as form_id, approval, notice from presentation " +
//            "where id = :id" +
//        ") as notice " +
//        "where approval is not null " +
//            "and approval between now() and date_add(now(), interval 3 day) " +
//        "order by approval desc"
//    , nativeQuery = true)
//    List<NoticeDTO> getNotice(@Param("id") Long id);

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
                "END" +
                ") " +
                "FROM Visitors v, Equipment e, Presentation p " +
                "WHERE (v.id = :id OR e.id = :id OR p.id = :id) " +
                "AND (v.approval IS NOT NULL OR e.approval IS NOT NULL OR p.approval IS NOT NULL) " +
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

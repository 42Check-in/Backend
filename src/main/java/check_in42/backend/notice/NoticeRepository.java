package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface NoticeRepository extends JpaRepository<NoticeDTO, Long> {

    @Query(value =
        "select * from (" +
            "select 'visitor' as category, id as form_id, approval, notice from visitor " +
            "where id = :id " +
            "union " +
            "select 'equipment' as category, id as form_id, approval, notice from equipment " +
            "where id = :id " +
            "union " +
            "select 'presentation' as category, id as form_id, approval, notice from presentation " +
            "where id = :id" +
        ") as notice " +
        "where approval is not null " +
            "and approval between now() and date_add(now(), interval 3 day) " +
        "order by approval desc"
    , nativeQuery = true)
    List<NoticeDTO> getNotice(@Param("id") Long id);
}

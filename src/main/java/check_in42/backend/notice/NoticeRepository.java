package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface NoticeRepository extends JpaRepository<NoticeDTO, Long> {

    @Query(value =
        "select * from (" +
            "select 'visitor' as category, id as form_id, agree_date, notice from visitor " +
            "where id = :id " +
            "union " +
            "select 'equipment' as category, id as form_id, agree_date, notice from equipment " +
            "where id = :id " +
            "union " +
            "select 'presentation' as category, id as form_id, agree_date, notice from presentation " +
            "where id = :id" +
        ") as notice " +
        "where agree_date is not null " +
            "and agree_date between now() and date_add(now(), interval 3 day) " +
        "order by agree_date desc"
    , nativeQuery = true)
    List<NoticeDTO> getNotice(Long id);
}

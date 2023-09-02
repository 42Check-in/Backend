package check_in42.backend.notice;

import check_in42.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("delete from Notice n where n.approval <= CURRENT_TIMESTAMP - :threeDaysAgo")
    void deleteByThreeDaysAgo(@Param("threeDaysAgo") Date threeDaysAgo);

    @Query("select n from Notice n where n.user = :user")
    List<Notice> findAllByUser(@Param("user") User user);
}

//@RequiredArgsConstructor
//@Slf4j
//public class NoticeRepository {
//
//    final EntityManager em;
//
//    public List<NoticeDTO> getNotice() {
//        final String jpql = "SELECT " +
//                "CAST(0 as SIGNED INTEGER) AS category, id AS form_id, notice " +
//                "FROM visitors " +
//                "WHERE user_id = 155 " +
//                "UNION " +
//                "SELECT " +
//                "CAST(1 AS SIGNED INTEGER) AS category, id AS form_id, notice " +
//                "FROM equipment " +
//                "WHERE user_id = 155 " +
//                "UNION " +
//                "SELECT " +
//                "CAST(2 AS SIGNED INTEGER) AS category, id AS form_id, notice " +
//                "FROM presentation " +
//                "WHERE user_id = 155";
//        Query nativeQuery = em.createNativeQuery(jpql, "NoticeMapping");
//        return nativeQuery.getResultList();
//    }
//}

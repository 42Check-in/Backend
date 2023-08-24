package check_in42.backend.visitors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    @Query("select v from Visitors v where v.approval is not null and v.approval >= :threeDaysAgo")
    List<Visitors> findApprovalList(@Param("threeDaysAgo") LocalDate threeDaysAgo);

    @Query("select v from Visitors v where not v.notice")
    List<Visitors> findByNoticeFalse();

}

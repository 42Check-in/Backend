package check_in42.backend.visitors;

import check_in42.backend.notice.utils.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    @Query("SELECT v FROM Visitors v " +
            "LEFT JOIN Notice n ON v.id = n.formId AND n.category = :categoryType " +
            "ORDER BY " +
            "CASE WHEN n.formId IS NULL THEN 1 ELSE 0 END DESC, " +
            "CASE WHEN n.formId IS NULL THEN v.id END DESC, " +
            "CASE WHEN n.formId IS NOT NULL THEN v.id END DESC")
    List<Visitors> findAllNotApprovalFirst(@Param("categoryType") Integer categoryType);

}

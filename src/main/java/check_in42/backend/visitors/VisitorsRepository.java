package check_in42.backend.visitors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
}

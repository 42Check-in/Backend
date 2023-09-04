package check_in42.backend.presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PresenRepository extends JpaRepository<Presentation, Long> {

    Page<Presentation> findByStatus(String status, Pageable pageable);

    Page<Presentation> findByStatusNot(String status, Pageable pageable);
}

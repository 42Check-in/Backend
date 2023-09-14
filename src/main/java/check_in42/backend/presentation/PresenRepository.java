package check_in42.backend.presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PresenRepository extends JpaRepository<Presentation, Long> {

    Page<Presentation> findByStatusOrderByDateAsc(String status, Pageable pageable);

    Page<Presentation> findByStatusNotOrderByDateAsc(String status, Pageable pageable);


    Page<Presentation> findAllByOrderByDateAsc(Pageable pageable);
}

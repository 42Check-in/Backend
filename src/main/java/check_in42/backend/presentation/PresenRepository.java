package check_in42.backend.presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PresenRepository extends JpaRepository<Presentation, Long> {

    Page<Presentation> findByStatusOrderByDate(String status, Pageable pageable);

    Page<Presentation> findByStatusNotOrderByDate(String status, Pageable pageable);


    Page<Presentation> findAllByDate(Pageable pageable);
}

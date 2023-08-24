package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceCheckDayRepository extends JpaRepository<ConferenceCheckDay, Long> {

    @Query("select c from ConferenceCheckDay c where c.year = :year and c.month = :month")
    ConferenceCheckDay findByYearMonth(@Param("year") String year, @Param("month") String month);
}

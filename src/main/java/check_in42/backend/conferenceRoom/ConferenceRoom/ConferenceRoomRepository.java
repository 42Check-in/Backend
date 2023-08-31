package check_in42.backend.conferenceRoom.ConferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

    @Query("select sum(c.reservationCount) from ConferenceRoom c where c.date = :date")
    long getSumReservationCountByDate(@Param("date") LocalDate date);

    @Query("select c from ConferenceRoom c where c.date = :date")
    List<ConferenceRoom> findByDate(@Param("date") LocalDate date);
}

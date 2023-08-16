package check_in42.backend.conferenceRoom.ConferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

    @Query("select sum(c.reservationCount) from ConferenceRoom c where c.date = :date")
    long getSumReservationCountByDate(String date);

    @Query("select c from ConferenceRoom c where date_format(c.date, '%d') = :day")
    List<ConferenceRoom> findByDay(Long day);

    @Query(value = "select * from conference_room " +
            "where date = :date " +
            "and (reservationInfo & :reqPlaceInfoBit) = :reqPlaceInfoBit",
            nativeQuery = true)
    List<ConferenceRoom> findByDateAndSamePlace(String date, Long reqPlaceInfoBit);
}

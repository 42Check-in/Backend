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

    List<ConferenceRoom> findAllByDate(@Param("date") LocalDate date);

    @Query(value = "select * from conference_room " +
            "where date = :date " +
            "and (reservation_info & :placeInfo) = :placeInfo " +
            "and (reservation_info & :timeBit) > 0 " +
            "order by reservation_info asc",
            nativeQuery = true)
    List<ConferenceRoom> findAllByPlaceAndNowOver(@Param("date") LocalDate date,
                                                  @Param("placeInfo") Long placeInfo,
                                                  @Param("timeBit") Long timeBit);

//    내 폼중에 내가 신청한 폼과 같은 시간대에 있는 애들
//    내가 신청한 폼과 같은 위치의 애들
    @Query(value = "select * from conference_room " +
            "where date = :date " +
            "and ((reservation_info & :reqPlaceInfoBit) = :reqPlaceInfoBit " +
            "or (user_id = :userId and (reservation_info & :reqTimeBit) > 0))",
            nativeQuery = true)
    List<ConferenceRoom> findByDateAndSamePlaceOrMySameTime(@Param("userId") Long userId, @Param("date") LocalDate date,
                                                @Param("reqPlaceInfoBit") Long reqPlaceInfoBit,
                                                @Param("reqTimeBit") Long reqTimeBit);

    @Query("DELETE FROM ConferenceRoom c WHERE c.date < :oneWeek")
    void deleteAllByDateBeforeOneWeek(@Param("oneWeek") LocalDate oneWeek);
}

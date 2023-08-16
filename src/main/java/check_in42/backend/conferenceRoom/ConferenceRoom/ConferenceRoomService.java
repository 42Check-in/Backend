package check_in42.backend.conferenceRoom.ConferenceRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenceRoomService {
    private final ConferenceRoomRepository conferenceRoomRepository;

    public Long join(ConferenceRoom conferenceRoom) {
        conferenceRoomRepository.save(conferenceRoom);

        return conferenceRoom.getId();
    }
    public List<ConferenceRoom> findConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findOne(Long id) {
        return conferenceRoomRepository.findOne(id);
    }

    public Long getSumReservationCountByDate(String date) {
        return conferenceRoomRepository.getSumReservationCountByDate(date);
    }

    public List<ConferenceRoom> findByDay(Long day) {
        return conferenceRoomRepository.findByDay(day);
    }

    public List<ConferenceRoom> findByDateAndSamePlace(String date, Long reqPlaceInfoBit) {
        return conferenceRoomRepository.findByDateAndSamePlace(date, reqPlaceInfoBit);
    }
}

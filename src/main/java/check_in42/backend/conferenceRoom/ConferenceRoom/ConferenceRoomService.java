package check_in42.backend.conferenceRoom.ConferenceRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConferenceRoomService {
    private final ConferenceRoomRepository conferenceRoomRepository;

    @Transactional
    public Long join(ConferenceRoom conferenceRoom) {
        conferenceRoomRepository.save(conferenceRoom);

        return conferenceRoom.getId();
    }

    @Transactional
    public Long deleteById(Long id) {
        conferenceRoomRepository.deleteById(id);

        return id;
    }

    public List<ConferenceRoom> findConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findOne(Long id) {
        return conferenceRoomRepository.findById(id).get();
    }

    public long getSumReservationCountByDate(String date) {
        return conferenceRoomRepository.getSumReservationCountByDate(date);
    }

    public List<ConferenceRoom> findByDay(Long day) {
        return conferenceRoomRepository.findByDay(day);
    }

    public List<ConferenceRoom> findByDateAndSamePlace(String date, Long reqPlaceInfoBit) {
        return conferenceRoomRepository.findByDateAndSamePlace(date, reqPlaceInfoBit);
    }
}

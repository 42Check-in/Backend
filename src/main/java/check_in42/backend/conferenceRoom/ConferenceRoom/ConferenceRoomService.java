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

    public List<ConferenceRoom> findByDay(Long day) {
        return conferenceRoomRepository.findByDay(day);
    }
}

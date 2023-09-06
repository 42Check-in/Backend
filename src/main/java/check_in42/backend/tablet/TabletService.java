package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceCheckOut.ConferenceCheckOutRepository;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TabletService {
    private final ConferenceRoomRepository conferenceRoomRepository;

    public List<ConferenceRoomDTO> findAllByPlaceAndNowOver(LocalDate date, Long placeInfo, Long timeBit) {
        List<ConferenceRoomDTO> result = new ArrayList<>();
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findAllByPlaceAndAfterNow(date, placeInfo, timeBit);
        conferenceRooms.forEach(conferenceRoom -> result.add(ConferenceRoomDTO.create(conferenceRoom)));
        return result;
    }

    @Transactional
    public void updateState(Long formId) {
        conferenceRoomRepository.findById(formId).get().setCheckInState(true);
    }
}

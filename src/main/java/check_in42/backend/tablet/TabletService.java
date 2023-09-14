package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomRepository;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TabletService {
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceRoomService conferenceRoomService;

    public List<ConferenceRoomDTO> findAllByPlaceAndNowOver(LocalDate date, Long placeInfo, Long timeBit) {
        List<ConferenceRoomDTO> result = new ArrayList<>();
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findAllByPlaceAndAfterNow(date, placeInfo, timeBit);
        conferenceRooms.forEach(conferenceRoom -> result.add(ConferenceRoomDTO.create(conferenceRoom)));
        return result;
    }

    @Transactional
    public void updateTime(Long formId) {
        conferenceRoomService.findOne(formId).setCheckInTime(LocalDateTime.now());
    }
}

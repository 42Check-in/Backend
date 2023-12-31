package check_in42.backend.conferenceRoom.ConferenceCheckOut;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomRepository;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConferenceCheckOutService {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceCheckOutRepository conferenceCheckOutRepository;
    private final ConferenceRoomService conferenceRoomService;

    public ConferenceCheckOut create (ConferenceRoomDTO conferenceRoomDTO, User user) {
        return ConferenceCheckOut.builder()
                .user(user)
                .date(conferenceRoomDTO.getDate())
                .checkInDateTime(conferenceRoomDTO.getCheckInTime())
                .checkOutDateTime(LocalDateTime.now())
                .reservationInfo(conferenceRoomDTO.getReservationInfo())
                .build();
    }

    @Transactional
    public Long join(ConferenceCheckOut conferenceCheckOut) {
        conferenceCheckOutRepository.save(conferenceCheckOut);
        return conferenceCheckOut.getId();
    }

    @Transactional
    public Long inputCheckOut(Long formId) {
        ConferenceRoom conferenceRoom = conferenceRoomService.findOne(formId);
        ConferenceRoomDTO conferenceRoomDTO = ConferenceRoomDTO.create(conferenceRoom);
        conferenceCheckOutRepository.save(create(conferenceRoomDTO, conferenceRoom.getUser()));
        return formId;
    }

}

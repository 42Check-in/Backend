package check_in42.backend.conferenceRoom.ConferenceCheckOut;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConferenceCheckOutService {

    private final ConferenceCheckOutRepository conferenceCheckOutRepository;

    public ConferenceCheckOut create (ConferenceRoomDTO conferenceRoomDTO, User user) {
        return ConferenceCheckOut.builder()
                .user(user)
                .date(conferenceRoomDTO.getDate())
                .checkOutDateTime(LocalDateTime.now())
                .reservationInfo(conferenceRoomDTO.getReservationInfo())
                .build();
    }

    @Transactional
    public Long join(ConferenceCheckOut conferenceCheckOut) {
        conferenceCheckOutRepository.save(conferenceCheckOut);
        return conferenceCheckOut.getId();
    }
}

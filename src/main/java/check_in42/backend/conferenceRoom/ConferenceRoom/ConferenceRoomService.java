package check_in42.backend.conferenceRoom.ConferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBitSize;
import check_in42.backend.conferenceRoom.ConferenceEnum.RoomCount;
import check_in42.backend.conferenceRoom.ConferenceUtil;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfo;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConferenceRoomService {
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final UserService userService;

    private final static Long reservationAllTimeNum = RoomCount.GAEPO.getValue() * 24 + RoomCount.SEOCHO.getValue() * 24;

    @Transactional
    public Long join(String intraId, ConferenceRoomDTO conferenceRoomDTO) {
        User user = userService.findByName(intraId);
        ConferenceRoom conferenceRoom = create(conferenceRoomDTO, user);

        conferenceRoomRepository.save(conferenceRoom);
        user.addConferenceForm(conferenceRoom);
        return conferenceRoom.getId();
    }

    @Transactional
    public Long deleteById(User user, Long formId) {
        conferenceRoomRepository.deleteById(formId);
        user.deleteConferenceRoomForm(formId);
        return formId;
    }

    public ConferenceRoom create(ConferenceRoomDTO conferenceRoomDTO, User user) {
        return ConferenceRoom.builder()
                .user(user)
                .date(conferenceRoomDTO.getDate())
                .reservationCount(ConferenceUtil.BitN(conferenceRoomDTO.getReservationInfo() & PlaceInfoBit.TIME.getValue()))
                .reservationInfo(conferenceRoomDTO.getReservationInfo())
                .build();
    }

    public List<ConferenceRoom> findConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findOne(Long id) {
        return conferenceRoomRepository.findById(id).get();
    }

    public Map<String, Long[]> makeBase() {
        Map<String, Long[]> result = new HashMap<String, Long[]>();
        PlaceInfo[] placeInfos = PlaceInfo.values();
        RoomCount[] roomInfos = RoomCount.values();

        for (int i = 0; i < placeInfos.length; i++)
            result.put(placeInfos[i].getValue(), new Long[roomInfos[i].getValue().intValue()]);
        return result;
    }

    public void setReservedInfo(Map<String, Long[]> result, Long day) {
        Long[] reservationInfo;
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findByDay(day);

        for (ConferenceRoom cr: conferenceRooms) {
            reservationInfo = ConferenceUtil.setReservationInfo(cr.getReservationInfo());
            Long[] rooms = ConferenceUtil.getRooms(result, ConferenceUtil.BitIdx(reservationInfo[0]));
            rooms[ConferenceUtil.BitIdx(reservationInfo[1])] |= reservationInfo[2];
        }
    }

    public boolean isInputForm(ConferenceRoomDTO conferenceRoomDTO) {
        List<ConferenceRoom> reservationList = conferenceRoomRepository.findByDateAndSamePlace(conferenceRoomDTO.getDate().toString(),
                conferenceRoomDTO.getReservationInfo() >> PlaceInfoBitSize.TIME.getValue());
        long emptyTime, reservationTimeBit, reqFormReservationTimeBit;

        emptyTime = 0;
        for (ConferenceRoom rcr: reservationList) {
            reservationTimeBit = rcr.getReservationInfo() & PlaceInfoBit.TIME.getValue();
            emptyTime |= reservationTimeBit;
        }
        reqFormReservationTimeBit = conferenceRoomDTO.getReservationInfo() & PlaceInfoBit.TIME.getValue();
        return (emptyTime & reqFormReservationTimeBit) == 0;
    }

    public boolean isDayFull(String date) {
        return conferenceRoomRepository.getSumReservationCountByDate(date) >= reservationAllTimeNum;
    }
}

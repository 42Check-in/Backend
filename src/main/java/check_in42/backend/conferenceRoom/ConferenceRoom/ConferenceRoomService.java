package check_in42.backend.conferenceRoom.ConferenceRoom;

import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceEnum.RoomCount;
import check_in42.backend.conferenceRoom.ConferenceUtil;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfo;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ConferenceRoomService {
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final UserService userService;

    private final static Long reservationAllTimeNum = RoomCount.GAEPO.getValue() * 24 + RoomCount.SEOCHO.getValue() * 24;

    @Transactional
    public Long join(ConferenceRoom conferenceRoom) {
        conferenceRoomRepository.save(conferenceRoom);
        return conferenceRoom.getId();
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

    public List<ConferenceRoomDTO> findByDateConferenceRooms(LocalDate date) {
        List<ConferenceRoomDTO> result = new ArrayList<>();
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findByDate(date);
        conferenceRooms.forEach(conferenceRoom -> result.add(ConferenceRoomDTO.create(conferenceRoom)));
        return result;
    }

    public Map<String, long[]> makeBase() {
        Map<String, long[]> result = new HashMap<>();
        PlaceInfo[] placeInfos = PlaceInfo.values();
        RoomCount[] roomInfos = RoomCount.values();

        for (int i = 0; i < placeInfos.length; i++)
            result.put(placeInfos[i].getValue(), new long[roomInfos[i].getValue().intValue()]);
        return result;
    }

    public boolean setReservedInfo(Map<String, long[]> result, LocalDate date) {
        Long[] reservationInfo;
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findByDate(date);

        log.info("conferenceRoom 개수: " + conferenceRooms.size());
        for (ConferenceRoom cr: conferenceRooms) {
            log.info("데이터 넣어");
            log.info("reservationInfo: " + cr.getReservationInfo() + " => " + Long.toBinaryString(cr.getReservationInfo()));
            reservationInfo = ConferenceUtil.setReservationInfo(cr.getReservationInfo());
            long[] rooms = ConferenceUtil.getRooms(result, ConferenceUtil.BitIdx(reservationInfo[0]));
            if (rooms == null) {
                log.info("reservationInfo 이상함");
                return false;
            }
            rooms[ConferenceUtil.BitIdx(reservationInfo[1])] |= reservationInfo[2];
        }
        log.info("정상 종료");
        return true;
    }

    public boolean isInputForm(ConferenceRoomDTO conferenceRoomDTO) {
        List<ConferenceRoom> reservationList = conferenceRoomRepository.findByDateAndSamePlace(conferenceRoomDTO.getDate(),
                conferenceRoomDTO.getReservationInfo() & ~PlaceInfoBit.TIME.getValue());
        long emptyTime, reservationTimeBit, reqFormReservationTimeBit;

        emptyTime = 0;
        for (ConferenceRoom rcr: reservationList) {
            reservationTimeBit = rcr.getReservationInfo() & PlaceInfoBit.TIME.getValue();
            emptyTime |= reservationTimeBit;
        }
        reqFormReservationTimeBit = conferenceRoomDTO.getReservationInfo() & PlaceInfoBit.TIME.getValue();
        return (emptyTime & reqFormReservationTimeBit) == 0;
    }

    @Transactional
    public Long inputForm(ConferenceRoomDTO conferenceRoomDTO, UserInfo userInfo) {
        User user = userService.findByName(userInfo.getIntraId()).orElseThrow(UserRunTimeException.NoUserException::new);
        ConferenceRoom conferenceRoom = create(conferenceRoomDTO, user);
        conferenceRoomRepository.save(conferenceRoom);
        user.addConferenceForm(conferenceRoom);
        return conferenceRoom.getId();
    }

    @Transactional
    public Long cancelForm(ConferenceRoomDTO conferenceRoomDTO, UserInfo userInfo) {
        User user = userService.findByName(userInfo.getIntraId()).orElseThrow(UserRunTimeException.NoUserException::new);
        conferenceRoomRepository.deleteById(conferenceRoomDTO.getFormId());
        user.deleteConferenceRoomForm(conferenceRoomDTO.getFormId());
        return conferenceRoomDTO.getFormId();
    }

    public boolean isDayFull(LocalDate date) {
        return conferenceRoomRepository.getSumReservationCountByDate(date) >= reservationAllTimeNum;
    }
}

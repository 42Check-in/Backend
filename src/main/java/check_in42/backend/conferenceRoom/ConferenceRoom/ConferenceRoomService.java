package check_in42.backend.conferenceRoom.ConferenceRoom;

import check_in42.backend.allException.FormException;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBitSize;
import check_in42.backend.conferenceRoom.ConferenceEnum.RoomCount;
import check_in42.backend.conferenceRoom.ConferenceUtil;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfo;
import check_in42.backend.conferenceRoom.exception.ConferenceException;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ConferenceRoomService {
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceCheckDayService conferenceCheckDayService;
    private final UserService userService;

    private final static Long reservationAllTimeNum = (RoomCount.GAEPO.getValue() + RoomCount.SEOCHO.getValue()) * PlaceInfoBitSize.TIME.getValue();

    @Transactional
    public Long join(ConferenceRoom conferenceRoom) {
        conferenceRoomRepository.save(conferenceRoom);
        return conferenceRoom.getId();
    }

    public ConferenceRoom create(ConferenceRoomDTO conferenceRoomDTO, User user) {
        return ConferenceRoom.builder()
                .user(user)
                .date(conferenceRoomDTO.getDate())
                .reservationCount(ConferenceUtil.bitN(conferenceRoomDTO.getReservationInfo() & PlaceInfoBit.TIME.getValue()))
                .reservationInfo(conferenceRoomDTO.getReservationInfo())
                .build();
    }

    public List<ConferenceRoom> findConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    public ConferenceRoom findOne(Long id) {
        return conferenceRoomRepository.findById(id)
                .orElseThrow(FormException.FormIdRunTimeException::new);
    }

    public Map<String, long[][]> makeBase() {
        Map<String, long[][]> result = new HashMap<>();
        PlaceInfo[] placeInfos = PlaceInfo.values();
        RoomCount[] roomInfos = RoomCount.values();

        for (int i = 0; i < placeInfos.length; i++)
            result.put(placeInfos[i].getValue(), new long[roomInfos[i].getValue().intValue()][2]);
        return result;
    }

    public void setReservedInfo(Map<String, long[][]> result, String intraId, LocalDate date) {
        Long[] reservationInfo;
        List<ConferenceRoom> conferenceRooms;
        if (date.isEqual(LocalDate.now()))
            conferenceRooms = conferenceRoomRepository.findAllByDateAndAfterNow(date, ConferenceUtil.getAfterNowBit());
        else
            conferenceRooms = conferenceRoomRepository.findAllByDate(date);

        log.info("conferenceRoom 개수: " + conferenceRooms.size());
        for (ConferenceRoom cr: conferenceRooms) {
            log.info("데이터 넣어");
            log.info("reservationInfo: " + cr.getReservationInfo() + " => " + Long.toBinaryString(cr.getReservationInfo()));
            reservationInfo = ConferenceUtil.setReservationInfo(cr.getReservationInfo());
            long[][] rooms = ConferenceUtil.getRooms(result, ConferenceUtil.bitIdx(reservationInfo[0]));
            int roomIdx = ConferenceUtil.bitIdx(reservationInfo[1]);
            if (rooms == null || roomIdx == -1) {
                log.info("reservationInfo 이상함");
                throw new ConferenceException.ReservationRunTimeException();
            }
            if (cr.getUser().getIntraId().equals(intraId))
                rooms[roomIdx][0] |= reservationInfo[2];
            else
                rooms[roomIdx][1] |= reservationInfo[2];
        }
        log.info("정상 종료");
    }

    public void isInputForm(ConferenceRoomDTO conferenceRoomDTO) {
        long reqFormReservationPlaceBit = conferenceRoomDTO.getReservationInfo() & ~PlaceInfoBit.TIME.getValue();
        long reqFormReservationTimeBit = conferenceRoomDTO.getReservationInfo() & PlaceInfoBit.TIME.getValue();
        log.info("userId==> " + conferenceRoomDTO.getUserId());
        log.info("date==> " + conferenceRoomDTO.getDate().toString());
        log.info("reqPlaceBit==> " + Long.toBinaryString(reqFormReservationPlaceBit));
        log.info("reqTimeBit==> " + Long.toBinaryString(reqFormReservationTimeBit));
        List<ConferenceRoom> reservationList = conferenceRoomRepository.findByDateAndSamePlaceOrMySameTime(
                conferenceRoomDTO.getUserId(),
                conferenceRoomDTO.getDate(),
                reqFormReservationPlaceBit,
                reqFormReservationTimeBit);

        reservationList.forEach(rcr -> {
            long reservationTimeBit = rcr.getReservationInfo() & PlaceInfoBit.TIME.getValue();
            if ((reservationTimeBit & reqFormReservationTimeBit) > 0) {
                if (rcr.getUser().getId().equals(conferenceRoomDTO.getUserId())) {
                    log.info("error_cod==> 1008");
                    throw new ConferenceException.DuplicateTimeException();
                }
                if ((rcr.getReservationInfo() & reqFormReservationPlaceBit) > 0) {
                    log.info("error_cod==> 1009");
                    throw new ConferenceException.AlreadyReserved();
                }
            }
        });
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
    public Long deleteForm(ConferenceRoomDTO conferenceRoomDTO) {
        ConferenceRoom conferenceRoom = findOne(conferenceRoomDTO.getFormId());
        conferenceCheckDayService.updateAllowCheckDay(conferenceRoom);
        conferenceRoom.getUser().deleteConferenceRoomForm(conferenceRoomDTO.getFormId());
        conferenceRoomRepository.deleteById(conferenceRoom.getId());
        return conferenceRoomDTO.getFormId();
    }

    public boolean isDayFull(LocalDate date) {
        return conferenceRoomRepository.getSumReservationCountByDate(date) >= reservationAllTimeNum;
    }

    public void deleteAllByDateBeforeOneWeek() {
        conferenceRoomRepository.deleteAllByDateBeforeOneWeek(LocalDate.now().minusWeeks(1));
    }
}

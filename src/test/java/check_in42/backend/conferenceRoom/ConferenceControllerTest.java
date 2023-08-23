package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfo;
import check_in42.backend.conferenceRoom.ConferenceEnum.RoomCount;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.myCheckIn.MyCheckInService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
class ConferenceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MyCheckInService myCheckInService;
    @Autowired
    private ConferenceCheckDayService conferenceCheckDayService;
    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void create_form() {
        User user = new User("hyeonsul", false);
        userService.join(user);

        ConferenceRoom test1 = ConferenceRoom.builder()
                .user(user)
                .date(LocalDate.parse("2023-07-11"))
                .reservationCount(4L)
                .reservationInfo(0B01000100000000000000001111000000L)
                .build();
        ConferenceRoom test2 = ConferenceRoom.builder()
                .user(user)
                .date(LocalDate.parse("2023-08-09"))
                .reservationCount(4L)
                .reservationInfo(0B01000100000000000000001111000000L)
                .build();
        ConferenceRoom test3 = ConferenceRoom.builder()
                .user(user)
                .date(LocalDate.parse("2023-08-09"))
                .reservationCount(4L)
                .reservationInfo(0B01000100000000000000000000001111L)
                .build();
        ConferenceRoom test4 = ConferenceRoom.builder()
                .user(user)
                .date(LocalDate.parse("2023-08-09"))
                .reservationCount(2L)
                .reservationInfo(0B01000100000001100000000000000000L)
                .build();
        ConferenceRoom test5 = ConferenceRoom.builder()
                .user(user)
                .date(LocalDate.parse("2023-08-09"))
                .reservationCount(3L)
                .reservationInfo(0B01000100001110000000000000000000L)
                .build();
        conferenceRoomService.join(test1);
        conferenceRoomService.join(test2);
        conferenceRoomService.join(test3);
        conferenceRoomService.join(test4);
        conferenceRoomService.join(test5);
        user.addConferenceForm(test1);
        user.addConferenceForm(test2);
        user.addConferenceForm(test3);
        user.addConferenceForm(test4);
        user.addConferenceForm(test5);

        ConferenceCheckDay conferenceCheckDay1 = ConferenceCheckDay.builder()
                .year(2023L)
                .month(8L)
                .days(0B0000000001001001000000100101000L).build();
        ConferenceCheckDay conferenceCheckDay2 = ConferenceCheckDay.builder()
                .year(2023L)
                .month(7L)
                .days(0B0000000001001001000100000010000L).build();
        conferenceCheckDayService.join(conferenceCheckDay1);
        conferenceCheckDayService.join(conferenceCheckDay2);
    }

    @Test
    void placeTime() throws Exception {
        String date = "2023-08-09";
        ObjectMapper mapper = new ObjectMapper();

        Map<String, long[]> result = conferenceRoomService.makeBase();
        conferenceRoomService.setReservedInfo(result, LocalDate.parse(date));

        System.out.println(Long.toBinaryString(result.get(PlaceInfo.GAEPO.getValue())[2]));
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }

    @Test
    void inputForm() throws Exception {
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
        conferenceRoomDTO.setReservationInfo(0B01000100110000000000000000000000L);
        conferenceRoomDTO.setDate(LocalDate.parse("2023-08-09"));
        if (!conferenceRoomService.isInputForm(conferenceRoomDTO)) {
            System.out.println("don`t input");
            return;
        }
        User user = userService.findByName("hyeonsul").get();
        ConferenceRoom conferenceRoom = conferenceRoomService.create(conferenceRoomDTO, user);
        conferenceRoomService.join(conferenceRoom);
        user.addConferenceForm(conferenceRoom);
        System.out.println("Input Form");
        System.out.println(conferenceRoomService.isInputForm(conferenceRoomDTO));

        System.out.println("=========================");
//        System.out.println(conferenceRoomService.isDayFull(conferenceRoom.getDate()));
        conferenceCheckDayService.updateDenyCheckDay(conferenceRoomDTO.getDate());
//        0B0000000001001001000000000101000L
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByDate(2023L, 8L);
        System.out.println(Long.toBinaryString(conferenceCheckDay.getDays()));
    }

    @Test
    void cancelForm() {
        User user = userService.findByName("hyeonsul").get();
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();

        conferenceRoomDTO.setDate(LocalDate.parse("2023-08-09"));
        System.out.println(">>>>>>>>>>>>" + conferenceRoomDTO.getDate());
        conferenceCheckDayService.updateAllowCheckDay(conferenceRoomDTO.getDate());

        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByDate(2023L, 8L);
        System.out.println(Long.toBinaryString(conferenceCheckDay.getDays()));
    }
}
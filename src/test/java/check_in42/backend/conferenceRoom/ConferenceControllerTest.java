package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
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

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
class ConferenceControllerTest {

    @Autowired
    private MockMvc mockMvc;
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
                .reservationCount(2L)
                .reservationInfo(0B01000100000000000110000000000000L)
                .build();
        conferenceRoomService.join(test1);
        conferenceRoomService.join(test2);

        ConferenceCheckDay conferenceCheckDay1 = ConferenceCheckDay.builder()
                .year(2023L)
                .month(8L)
                .days(0B0000000001001001000000000101000L).build();
        ConferenceCheckDay conferenceCheckDay2 = ConferenceCheckDay.builder()
                .year(2023L)
                .month(7L)
                .days(0B0000000001001001000100000010000L).build();
        conferenceCheckDayService.join(conferenceCheckDay1);
        conferenceCheckDayService.join(conferenceCheckDay2);
    }

    @Test
    void calender() throws Exception {
        Long year = 2023L;
        Long month = 8L;

        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByDate(year, month);
        System.out.println("calender 23-08 >>>>>>>>>> " + conferenceCheckDay.getDays());
        mockMvc.perform(MockMvcRequestBuilders.get("/calender/" + year + "/" + month)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(conferenceCheckDay.getDays().toString()));
    }

    @Test
    void placeTime() {
    }

    @Test
    void inputForm() {
    }

    @Test
    void cancelForm() {
    }
}
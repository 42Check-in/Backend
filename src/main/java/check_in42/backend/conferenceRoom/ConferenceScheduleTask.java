package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.user.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

//@Component
public class ConferenceScheduleTask {

    ConferenceRoomService conferenceRoomService;
    User user;

//    @Scheduled(cron = "0 0 * * MON")
    public void deleteConferenceRooms() {
        conferenceRoomService.deleteAllByDateBeforeOneWeek();
        List<ConferenceRoom> conferenceRooms = user.getConferenceRooms();
        conferenceRooms.forEach(c -> {
            if (c.getDate().isBefore(LocalDate.now().minusWeeks(1)))
                user.getConferenceRooms().remove(c);
        });
    }
}

package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import check_in42.backend.allException.FormException;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenceCheckDayService {

    private final ConferenceCheckDayRepository conferenceCheckDayRepository;

    public Long join(ConferenceCheckDay conferenceCheckDay) {
        conferenceCheckDayRepository.save(conferenceCheckDay);

        return conferenceCheckDay.getId();
    }
    public List<ConferenceCheckDay> findConferenceCheckDays() {
        return conferenceCheckDayRepository.findAll();
    }

    public ConferenceCheckDay findOne(Long id) {
        return conferenceCheckDayRepository.findById(id)
                .orElseThrow(FormException.FormIdRunTimeException::new);
    }

    public ConferenceCheckDay findByYearMonth(Long year, Long month) {
        return conferenceCheckDayRepository.findByYearMonth(year, month);
    }

    @Transactional
    public Long updateDenyCheckDay(LocalDate formDate) {
        long year, month, day;

        year = formDate.getYear();
        month = formDate.getMonthValue();
        day = formDate.getDayOfMonth();
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayRepository.findByYearMonth(year, month);
        if (conferenceCheckDay == null) {
            conferenceCheckDay = ConferenceCheckDay.builder()
                    .year(year)
                    .month(month)
                    .days(1L << (day - 1))
                    .build();
            conferenceCheckDayRepository.save(conferenceCheckDay);
            return conferenceCheckDay.getId();
        }
        conferenceCheckDay.setDays(conferenceCheckDay.getDays() | (1L << (day - 1)));
        return conferenceCheckDay.getId();
    }

    public Long updateAllowCheckDay(ConferenceRoom conferenceRoom) {
        long year, month, day;

        year = conferenceRoom.getDate().getYear();
        month = conferenceRoom.getDate().getMonthValue();
        day = conferenceRoom.getDate().getDayOfMonth();
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayRepository.findByYearMonth(year, month);
        if (conferenceCheckDay == null)
            return 0L;
        conferenceCheckDay.setDays(conferenceCheckDay.getDays() & ~(1L << (day - 1)));
        return conferenceCheckDay.getId();
    }
}

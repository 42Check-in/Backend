package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import check_in42.backend.conferenceRoom.ConferenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return conferenceCheckDayRepository.findOne(id);
    }

    public ConferenceCheckDay findByDate(Long year, Long month) {
        return conferenceCheckDayRepository.findByDate(year, month);
    }

    @Transactional
    public Long updateCheckDay(LocalDate formDate) {
        Long year, month, day;

        year = Long.valueOf(formDate.getYear());
        month = Long.valueOf(formDate.getMonthValue());
        day = Long.valueOf(formDate.getDayOfMonth());
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayRepository.findByDate(year, month);
        if (conferenceCheckDay == null) {
            conferenceCheckDay = ConferenceCheckDay.builder()
                    .year(year)
                    .month(month)
                    .days(ConferenceUtil.getDayBit(year, month) ^ (1 << (day - 1)))
                    .build();
            conferenceCheckDayRepository.save(conferenceCheckDay);
            return conferenceCheckDay.getId();
        }
        conferenceCheckDay.setDays(conferenceCheckDay.getDays() & (1 << (day - 1)));
        return conferenceCheckDay.getId();
    }
}

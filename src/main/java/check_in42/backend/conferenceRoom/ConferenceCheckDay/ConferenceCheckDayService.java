package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import check_in42.backend.conferenceRoom.ConferenceUtil;
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
        return conferenceCheckDayRepository.findById(id).get();
    }

    public ConferenceCheckDay findByDate(String year, String month) {
        return conferenceCheckDayRepository.findByDate(year, month);
    }

    @Transactional
    public Long updateDenyCheckDay(LocalDate formDate) {
        long year, month, day;

        year = formDate.getYear();
        month = formDate.getMonthValue();
        day = formDate.getDayOfMonth();
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayRepository.findByDate(year, month);
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

    @Transactional
    public Long updateAllowCheckDay(LocalDate formDate) {
        long day;
        String year, month;


        year = Long.toString(formDate.getYear());
        month = Long.toString(formDate.getMonthValue());
        day = formDate.getDayOfMonth();
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayRepository.findByDate(year, month);
        if (conferenceCheckDay == null)
            return null;
        conferenceCheckDay.setDays(conferenceCheckDay.getDays() & ~(1L << (day - 1)));
        return conferenceCheckDay.getId();
    }
}

package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

package check_in42.backend.presentation;

import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.presentation.utils.PresentationStatus;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PresentationService {

    private final PresentationRepository presentationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long join(Presentation presentation) {
        presentationRepository.save(presentation);

        return presentation.getId();
    }

    public void delete(Long formId) {
        presentationRepository.delete(formId);
    }

    public Presentation findOne(Long id) {
        return presentationRepository.findOne(id);
    }

    public Presentation create(User user, PresentationDTO presentationDTO, int count) {
        return new Presentation(user, presentationDTO, count);
    }


    // 해당 월에서 신청자가 있으면 그 list, 없으면 yyyy-MM-dd만 보내
    public List<PresentationDTO> showMonthSchedule(String month) {
        // 해당 월에 작성한 모든 form들을 찾아옴.
        List<Presentation> allForms = presentationRepository.findOneMonth(month);

        LocalDate localMonth = LocalDate.parse(month);
        LocalDate firstWednesday = localMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        LocalDate lastDayOfMonth = localMonth.with(TemporalAdjusters.lastDayOfMonth());

        //4주 순회
        LocalDate wednesday = firstWednesday;
        List<PresentationDTO> res = new ArrayList<>();
        while (!wednesday.isAfter(lastDayOfMonth)) {

            List<Presentation> presentations = new ArrayList<>();
            for (Presentation form : allForms) {
                if (form.getDate().equals(wednesday)) {
                    presentations.add(form);
                }
            }
            PresentationDTO presentationDTO = new PresentationDTO();
            if (!presentations.isEmpty()) {
                for (Presentation pre : presentations) {
                    res.add(PresentationDTO.create(pre));
                }
            }
            else {
                presentationDTO.setDate(wednesday.toString());
                res.add(presentationDTO);
            }
            wednesday = wednesday.plusWeeks(1);
        }
        return res;
    }

    @Transactional
    public void findAndDelete(String intraId, Long formId) {
        final Presentation presentation = presentationRepository.findOne(formId);
        if (presentation.getStatus().equals(PresentationStatus.PENDING.getName())) {
            presentationRepository.setNextPresentation(presentation.getDate());
        }
        presentationRepository.delete(formId);
        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        user.deletePresentationForm(formId);
    }

    @Transactional
    public void setAgreeDatesAndStatus(Map<Long, Integer> presentation) {
//        for (Long id : formId) {
//            final Presentation presentation = presentationRepository.findOne(id);
//            presentation.setApproval();
//            presentation.setStatus(status);
//            presentationRepository.save(presentation); 안써도댐?
//        }
        for (Map.Entry<Long, Integer> entry : presentation.entrySet()) {
            final Presentation one = presentationRepository.findOne(entry.getKey());
            System.out.println(one.getId());
            one.setApproval();
            one.setStatus(entry.getValue());
            System.out.println(one.getStatus());
        }
    }

    public List<Presentation> findDataBeforeDay(int day) {
        return presentationRepository.findDataBeforeDay(day);
    }

    public List<Presentation> findAll() {
        return presentationRepository.findAll();
    }

    public List<PresentationDTO> findAllDESC() {
        final List<Presentation> presentationList = presentationRepository.findAllDESC();
        final List<PresentationDTO> result = presentationList.stream()
                .map(PresentationDTO::create)
                .collect(Collectors.toList());
        return result;
    }

    public List<Presentation> findByNoticeFalse() {
        return presentationRepository.findByNoticeFalse();
    }

    /*
    * DTO, entity를 갖고가서
    * dto의 field가 null이면 기존꺼를,
    * 아니라면 바꿔서 다시 set...음음음음
    * */
    @Transactional
    public void update(Long formId, PresentationDTO presentationDTO) {
        final Presentation presentation = presentationRepository.findOne(formId);

    }

    public List<Presentation> findByDate(String date) {
        return presentationRepository.findByDate(date);
    }

    @Transactional
    public Long createNewForm(final String intraId,
                              final PresentationDTO presentationDTO) {
        final User user = userRepository.findByName(intraId).get();
        final int count = presentationRepository.findByDate(presentationDTO.getDate()).size();
        final Presentation presentation = create(user, presentationDTO, count);
        log.info("-----------status?" + presentation.getStatus());
        final Long formId = join(presentation);
        user.addPresentationForm(presentation);

        return formId;
    }
}

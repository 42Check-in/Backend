package check_in42.backend.presentation;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.presentation.utils.PresentationStatus;
import check_in42.backend.presentation.utils.PresentationTime;
import check_in42.backend.presentation.utils.PresentationType;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PresentationService {

    private final PresentationRepository presentationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long join(Presentation presentation) {
        presentationRepository.save(presentation);

        return presentation.getId();
    }

    public void delete(Presentation presentation) {
        presentationRepository.delete(presentation);
    }

    public Presentation findOne(Long id) {
        return presentationRepository.findOne(id);
    }

    public Presentation create(User user, PresentationDTO presentationDTO, int count) {
        return new Presentation(user, presentationDTO, count);
    }

    public List<PresentationDTO> showMonthSchedule(String month) {
        List<Presentation> allForms = presentationRepository.findOneMonth(month);
        List<PresentationDTO> res = new ArrayList<>();
        for (Presentation form : allForms) {
            res.add(PresentationDTO.create(form));
        }

        return res;
    }

    @Transactional
    public void findAndDelete(String intraId, Long formId) {
        Presentation presentation = presentationRepository.findOne(formId);
        if (presentation.getStatus().equals(PresentationStatus.PENDING.getDescription())) {
            presentationRepository.setNextPresentation(presentation.getDate());
        }
        presentationRepository.delete(presentation);

        DeleteFormInUser(intraId, formId);
    }

    @Transactional
    public void DeleteFormInUser(String intraId, Long formId) {
        User user = userRepository.findByName(intraId).get();
        List<Presentation> allForm = user.getPresentations();

        for (Presentation presentation : allForm) {
            if (presentation.getId().equals(formId)) {
                allForm.remove(presentation);
                break;
            }
        }
        //cascade -> List 삭제 감지로 해도 되나..
    }

    @Transactional
    public void setAgreeDatesAndStatus(List<Long> formId, PresentationStatus status) {
        for (Long id : formId) {
            Presentation presentation = presentationRepository.findOne(id);
            presentation.setApproval();
            presentation.setStatus(status);
//            presentationRepository.save(presentation); 안써도댐?
        }
    }

    public List<Presentation> findDataBeforeDay(int day) {
        return presentationRepository.findDataBeforeDay(day);
    }

    public List<Presentation> findAll() {
        return presentationRepository.findAll();
    }

    public List<Presentation> findAllDESC() {
        return presentationRepository.findAllDESC();
    }

    public List<Presentation> findByNoticeFalse() {
        return presentationRepository.findByNoticeFalse();
    }

    public List<Presentation> findByDate(String date) {
        return presentationRepository.findByDate(date);
    }
}

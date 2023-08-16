package check_in42.backend.presentation;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Presentation create(String intraId, PresentationDTO presentationDTO) {
        return new Presentation(intraId, presentationDTO);
    }

    public List<PresentationDTO> showMonthSchedule(String month) {
        List<Presentation> allForms = presentationRepository.findOneMonth(month);
        List<PresentationDTO> res = new ArrayList<>();
        for (Presentation form : allForms) {
            res.add(PresentationDTO.create(form.getId(), form.getUserName(), form.getSubject(),
                    form.getDate(), form.getType().ordinal(), form.getDetail(), form.getContents(),
                    form.getTime().ordinal(), form.getScreen()));
        }

        return res;
    }

    @Transactional
    public void addPresentationToUser(String intraId, Presentation presentation) {
        User user = userRepository.findByName(intraId);
        user.addPresentationForm(presentation);
    }

    @Transactional
    public void findAndDelete(String intraId, Long formId) {
        Presentation presentation = presentationRepository.findOne(formId);
        presentationRepository.delete(presentation);

        DeleteFormInUser(intraId, formId);
    }

    @Transactional
    public void DeleteFormInUser(String intraId, Long formId) {
        User user = userRepository.findByName(intraId);
        List<Presentation> allForm = user.getPresentations();

        for (Presentation presentation : allForm) {
            if (presentation.getId().equals(formId)) {
                allForm.remove(presentation);
                break;
            }
        }
        //cascade -> List 삭제 감지
    }
}

package check_in42.backend.presentation;

import check_in42.backend.presentation.utils.PresentationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PresentationService {

    private final PresentationRepository presentationRepository;

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

    }
}

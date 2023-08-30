package check_in42.backend.notice;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.VisitorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final VisitorsService visitorsService;
    private final EquipmentService equipmentService;
    private final PresentationService presentationService;

    public List<NoticeDTO> showNotice(Long id) {
        return noticeRepository.getNotice(id);
    }

    @Transactional
    public Long updateNotice(Long id) {
        final List<Visitors> visitorsList = visitorsService.findByNoticeFalse();
        final List<Presentation> presentationList = presentationService.findByNoticeFalse();
        final List<Equipment> equipmentList = equipmentService.findByNoticeFalse();

        for (Visitors visitors: visitorsList) {
            visitors.setNotice(true);
        }
        for (Presentation presentation: presentationList) {
            presentation.setNotice(true);
        }
        for (Equipment equipment: equipmentList) {
            equipment.setNotice(true);
        }

        return id;
    }
}

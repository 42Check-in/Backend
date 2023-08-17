package check_in42.backend.notice;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.notice.utils.CategoryType;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.VisitorsService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserService userService;
    private final VisitorsService visitorsService;
    private final EquipmentService equipmentService;
    private final PresentationService presentationService;

    public List<NoticeDTO> showNotice(Long id) {
        return noticeRepository.getNotice(id);
    }

    public Long updateNotice(Long id) {
        List<Visitors> visitorsList = visitorsService.
    }
}

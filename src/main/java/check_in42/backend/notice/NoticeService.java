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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserService userService;
    private final VisitorsService visitorsService;
    private final EquipmentService equipmentService;
    private final PresentationService presentationService;

    public List<NoticeDTO> showNotice(User user) {
        final List<Visitors> visitorsList = visitorsService.findByApproval();
        final List<Presentation> presentationList = presentationService.findByApproval();
        final List<Equipment> equipmentList = equipmentService.findByApproval();
        List<NoticeDTO> visitorsResult = visitorsList.stream().map(visitors -> NoticeDTO.builder()
                .category(CategoryType.VISITORS.ordinal())
                .formId(visitors.getId())
                .date(visitors.getApproval())
                .check(false)
                .build()).collect(Collectors.toList());
        List<NoticeDTO> presentationResult = presentationList.stream().map(presentation -> NoticeDTO.builder()
                .category(CategoryType.PRESENTATION.ordinal())
                .formId(presentation.getId())
                .date(presentation.getAgreeDate())
                .check(false)
                .build()).collect(Collectors.toList());
        List<NoticeDTO> equipmentResult = equipmentList.stream().map(equipment -> NoticeDTO.builder()
                .category(CategoryType.EQUIPMENT.ordinal())
                .formId(equipment.getId())
                .date(equipment.getAgreeDate())
                .check(false)
                .build()).collect(Collectors.toList());
        List<NoticeDTO> result = new ArrayList<>();
        result.addAll(visitorsResult);
        result.addAll(presentationResult);
        result.addAll(equipmentResult);
        Collections.sort(result, new Comparator<NoticeDTO>(){
            @Override
            public int compare(NoticeDTO o1, NoticeDTO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        }.reversed());
        return result;
    }

    public List<NoticeDTO> checkNotice(List<NoticeDTO> notices) {
        notices.forEach(NoticeDTO::checkNotice);
        return notices;
    }
}

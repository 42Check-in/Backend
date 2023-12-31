package check_in42.backend.equipments;

import check_in42.backend.equipments.utils.EquipmentDTO;
import check_in42.backend.equipments.utils.ResponseEquipment;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final EquipmentRepo equipmentRepo;

    @Transactional
    public Long join(Equipment equipment) {
        equipmentRepository.save(equipment);

        return equipment.getId();
    }

    public Equipment getFormByIntraId(String intraId, Long formId) {
        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Equipment> allForm = user.getEquipments();

        for (Equipment equip : allForm) {
            if (equip.getId().equals(formId))
                return equip;
        }
        return null;
    }
    public Equipment findOne(Long id){
        return equipmentRepository.findOne(id).get();
    }

    @Transactional
    public void findAndDelete(String intraId, Long formId) {
        final Equipment equipment = equipmentRepository.findOne(formId).get();
        equipmentRepository.delete(equipment);

        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        user.deleteEquipForm(formId);
    }

    public Equipment create(User user, EquipmentDTO equipmentDTO) {
        return new Equipment(user, equipmentDTO);
    }
    public List<EquipmentDTO> showAllFormByName(String intraId) {
        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);

        final List<Equipment> equipments = user.getEquipments();
        LocalDate now = LocalDate.now();
        final List<EquipmentDTO> res = new ArrayList<>();

        for (Equipment equip : equipments) {
            if ((equip.getReturnDate().isAfter(now) || equip.getReturnDate().equals(now))
                    && equip.getApproval() != null){
                res.add(EquipmentDTO.create(equip));
            }
        }
        return res;
    }
    @Transactional
    public Long extendDate(EquipmentDTO equipmentDTO) {

        final Equipment equipment = findOne(equipmentDTO.getFormId());
        equipment.updateForExtension(equipmentDTO);
        join(equipment);
        return equipment.getId();
    }


    // 수락 떨어지면 현재로 setDate
    @Transactional
    public void setAgreeDates(List<Long> formId) {

        for (Long id : formId) {
            Equipment form = equipmentRepository.findOne(id).get();
            if (form.getExtension() == 1) {
                form.extendReturnDateByPeriod(form.getPeriod());
                form.setApproval();
            } else {
                form.setApproval();
            }
        }
    }

    // 알림창에 띄울거, 보컬으로부터 수락이 떨어진 뒤
    public List<Equipment> findDataBeforeDay(int day) {
        return equipmentRepository.findDataBeforeDay(day);
    }

    public List<EquipmentDTO> findAllDESC() {
        final List<Equipment> equipmentList = equipmentRepository.findAllDESC();
        final List<EquipmentDTO> result = equipmentList.stream()
                .sorted(Comparator.comparing(Equipment::getApproval, Comparator.nullsFirst(Comparator.reverseOrder())))
                .map(EquipmentDTO::create)
                .collect(Collectors.toList());
        return result;
    }

    public List<EquipmentDTO> findAll() {
        final List<Equipment> equipmentList = equipmentRepository.findAll();
        final List<EquipmentDTO> result = equipmentList.stream()
                .map(EquipmentDTO::create)
                .collect(Collectors.toList());
        return result;
    }


    public List<Equipment> findByNoticeFalse() {
        return equipmentRepository.findByNoticeFalse();
    }

    @Transactional
    public Long createNewForm(String intraId, EquipmentDTO equipmentDTO) {

        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final Equipment equipment = create(user, equipmentDTO);
        final Long formId = join(equipment);
        user.addEquipForm(equipment);
        return formId;
    }

    public ResponseEquipment findAllApproval(Pageable pageable) {
        final Page<Equipment> equipment = equipmentRepo.findByApprovalIsNotNull(pageable);

        final List<EquipmentDTO> equipmentDTOS = equipment.getContent().stream().map(EquipmentDTO::create).toList();
        final int count = equipment.getTotalPages();

        ResponseEquipment res = ResponseEquipment.create(equipmentDTOS, count);

        return res;
    }

    public ResponseEquipment findAllNotApproval(Pageable pageable) {
        final Page<Equipment> equipment = equipmentRepo.findByApprovalIsNull(pageable);

        final List<EquipmentDTO> equipmentDTOS = equipment.getContent().stream().map(EquipmentDTO::create).toList();
        final int count = equipment.getTotalPages();

        ResponseEquipment res = ResponseEquipment.create(equipmentDTOS, count);

        return res;
    }
}

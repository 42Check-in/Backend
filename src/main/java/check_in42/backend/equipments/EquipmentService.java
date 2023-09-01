package check_in42.backend.equipments;

import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.Visitors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    /*
     * intraid -> user 특정 가능
     * user가 갖고 있는 forms들을 모두 가져온 후 DTO에 맞춰서 생성, List에 담아서 내보내기
     * */
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

    /*
    *   1. String period
        2. Sting returnDate
        3. Long formId
    * */
    @Transactional
    public Long extendDate(EquipmentDTO equipmentDTO) {

//        final Equipment equipment = getFormByIntraId(intraId, equipmentDTO.getFormId());
        final Equipment equipment = findOne(equipmentDTO.getFormId());
        log.info("-----------before id?" + equipment.getId());
        equipment.updateForExtension(equipmentDTO);
        Long res = join(equipment);
        log.info("-----------after id?" + res);

//        equipment.extendReturnDateByPeriod(equipmentDTO.getPeriod());

        //userList의 업데이트
//        final Equipment inUserForm = getFormByIntraId(intraId, equipmentDTO.getFormId());
//        if (inUserForm != null)
//            inUserForm.extendReturnDateByPeriod(equipmentDTO.getPeriod());

        return equipment.getId();
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
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
}

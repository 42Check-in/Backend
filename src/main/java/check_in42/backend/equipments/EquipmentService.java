package check_in42.backend.equipments;

import check_in42.backend.equipments.utils.EquipmentType;
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
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long join(Equipment equipment) {
        equipmentRepository.save(equipment);

        return equipment.getId();
    }

    public Equipment getFormByIntraId(String intraId, Long formId) {
        User user = userRepository.findByName(intraId);
        List<Equipment> allForm = user.getEquipments();

        for (Equipment equip : allForm) {
            if (equip.getId().equals(formId))
                return equip;
        }
        return null;
    }

    @Transactional
    public void DeleteFormInUser(String intraId, Long formId) {
        User user = userRepository.findByName(intraId);
        List<Equipment> allForm = user.getEquipments();

        for (Equipment equip : allForm) {
            if (equip.getId().equals(formId)) {
                allForm.remove(equip);
                break;
            }
        }
        /*
        * user 부분에서 setter 역할하는 formList 갈아끼우는 로직 필요한데 일단 손 안댓어여
        * 만약 이부분 추가된다면 transacrional 피료함
        * */
        //user.updateFormList(allForm);
        //userRepository.save(user);
    }

    public Equipment findOne(Long id){
        return equipmentRepository.findOne(id);
    }

    @Transactional
    public void findAndDelete(String intraId, Long formId) {
        // db에서 제거
        Equipment equipment = equipmentRepository.findOne(formId);
        equipmentRepository.delete(equipment);

        // userList에서 제거
        DeleteFormInUser(intraId, formId);
    }

    public Equipment create(User user, EquipmentDTO equipmentDTO) {
        return new Equipment(user, equipmentDTO);
    }


    /*
     * intraid -> user 특정 가능
     * user가 갖고 있는 forms들을 모두 가져온 후 DTO에 맞춰서 생성, List에 담아서 내보내기
     * */
    public List<EquipmentDTO> showAllFormByName(String intraId) {
        User user = userRepository.findByName(intraId);

        List<Equipment> equipments = user.getEquipments();
        LocalDate now = LocalDate.now();
        List<EquipmentDTO> res = new ArrayList<>();

        for (Equipment equip : equipments) {
            if (equip.getReturnDate().isAfter(now)) {
                res.add(EquipmentDTO.create(equip.getUserName(), equip.getPhoneNumber(), equip.getDate(), EquipmentType.valueOf(equip.getEquipment()).ordinal(),
                        equip.isPurpose(), equip.getDetail(), equip.getBenefit(), equip.getPeriod(), equip.getReturnDate()));
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
    public Long extendDate(String intraId, EquipmentDTO equipmentDTO) {
        //DB의 dirty checking 이용
        Equipment equipment = equipmentRepository.findOne(equipmentDTO.getFormId());
        equipment.extendReturnDateByPeriod(equipmentDTO.getPeriod());

        //userList의 업데이트
        Equipment inUserForm = getFormByIntraId(intraId, equipmentDTO.getFormId());
        if (inUserForm != null)
            inUserForm.extendReturnDateByPeriod(equipmentDTO.getPeriod());

        return equipment.getId();
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Transactional
    public void setAgreeDates(List<Long> formId) {
        for (Long id : formId) {
            equipmentRepository.findOne(id).setAgreeDate();
        }
    }

    public List<Equipment> findDataBeforeDay(int day) {
        return equipmentRepository.findDataBeforeDay(day);
    }
}

package check_in42.backend.equipments;

import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    public Long join(Equipment equipment) {
        equipmentRepository.save(equipment);

        return equipment.getId();
    }

    public Equipment findOne(Long id){
        return equipmentRepository.findOne(id);
    }

    public void findAndDelete(Long id) {
        Equipment equipment = equipmentRepository.findOne(id);
        equipmentRepository.delete(equipment);
    }

    public Equipment create(String intraId, EquipmentDTO equipmentDTO) {
        return new Equipment(intraId, equipmentDTO);
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
                res.add(EquipmentDTO.create(equip.getUserName(), equip.getPhoneNumber(), equip.getDate(), equip.getEquipment().ordinal(),
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
    public void extendDate(String intraId, EquipmentDTO equipmentDTO) {
        User user = userRepository.findByName(intraId);
        /* user에 이 사항을 어케 업뎃할까 */
        Equipment equipment = equipmentRepository.findOne(equipmentDTO.getFormId());
        equipment.extendReturnDateByPeriod(equipmentDTO.getPeriod());
    }
}

package check_in42.backend.equipments;

import check_in42.backend.equipments.utils.EquipmentType;
import lombok.Getter;

@Getter
public class EquipmentDTO {

    private String userName;
    private String phoneNumber;
    private String date;
    private int equipment;
    private boolean purpose;
    private String detail;
    private String benefit;
    private int period;
    private String returnDate;
    private Long formId;
    private String etc;

    public static EquipmentDTO create(Equipment equipment) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.userName = equipment.getUserName();
        equipmentDTO.phoneNumber = equipment.getPhoneNumber();
        equipmentDTO.date = equipment.getDate().toString();
        equipmentDTO.purpose = equipment.isPurpose();
        equipmentDTO.detail = equipment.getDetail();
        equipmentDTO.period = equipment.getPeriod();
        equipmentDTO.benefit = equipment.getBenefit();
        equipmentDTO.returnDate = equipment.getReturnDate().toString();
        equipmentDTO.equipment = EquipmentType.valueOf(equipment.getEquipment()).ordinal();

        return equipmentDTO;
    }
}


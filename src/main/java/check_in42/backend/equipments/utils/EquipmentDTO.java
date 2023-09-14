package check_in42.backend.equipments.utils;

import check_in42.backend.equipments.Equipment;
import lombok.Getter;

@Getter
public class EquipmentDTO {

    private String userName;
    private String phoneNumber;
    private String date;
    private int equipment;
    private int purpose;
    private String detail;
    private String benefit;
    private int period;
    private String returnDate;
    private Long formId;
    private String etcPurpose;
    private String etcEquipment;
    private int status;
    private int extension;
    private String intraId;
    private String time;


    //purpose, equipment
    public static EquipmentDTO create(Equipment equipment) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.userName = equipment.getUserName();
        equipmentDTO.phoneNumber = equipment.getPhoneNumber();
        equipmentDTO.date = equipment.getDate().toString();
        equipmentDTO.formId = equipment.getId();
        equipmentDTO.purpose = equipment.getPurpose().equals("42서울") ? 1 : 0;
        equipmentDTO.etcPurpose = equipmentDTO.purpose == 0 ? equipment.getPurpose() : null;
        equipmentDTO.detail = equipment.getDetail();
        equipmentDTO.period = equipment.getPeriod();
        equipmentDTO.benefit = equipment.getBenefit();
        equipmentDTO.returnDate = equipment.getReturnDate().toString();
        equipmentDTO.equipment = EquipmentType.getOrdinalByDescription(equipment.getEquipment());
        equipmentDTO.etcEquipment = equipmentDTO.equipment == 0 ?
                equipment.getEquipment() : null;
        equipmentDTO.status = equipment.getApproval() != null ? 1 : 0;
        equipmentDTO.extension = equipment.getExtension();
        equipmentDTO.intraId = equipment.getIntraId();
        equipmentDTO.time = equipment.getTime();
        return equipmentDTO;
    }
}


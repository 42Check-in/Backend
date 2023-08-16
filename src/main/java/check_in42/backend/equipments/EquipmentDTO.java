package check_in42.backend.equipments;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public static EquipmentDTO dummy(String userName, String phoneNumber, String date,
                              int equipment, boolean purpose, String detail, String benefit,
                              int period, String returnDate, Long formId) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.userName = userName;
        equipmentDTO.period = period;
        equipmentDTO.date = date;
        equipmentDTO.equipment = equipment;
        equipmentDTO.purpose = purpose;
        equipmentDTO.detail = detail;
        equipmentDTO.benefit = benefit;
        equipmentDTO.phoneNumber = phoneNumber;
        equipmentDTO.returnDate = returnDate;
        equipmentDTO.formId = formId;

        return equipmentDTO;
    }

    public static EquipmentDTO create(String userName, String phoneNumber, LocalDate date, int equipment,
                                      boolean purpose, String detail, String benefit, int period, LocalDate returnDate) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.userName = userName;
        equipmentDTO.phoneNumber = phoneNumber;
        equipmentDTO.date = date.toString();
        equipmentDTO.purpose = purpose;
        equipmentDTO.detail = detail;
        equipmentDTO.period = period;
        equipmentDTO.benefit = benefit;
        equipmentDTO.returnDate = returnDate.toString();
        equipmentDTO.equipment = equipment;

        return equipmentDTO;
    }
}


/*
* 1. String userName // 본명
2. String phoneNumber
3. String date
3. Long equipments // (enum)
4. boolean purpose
5. String detail // 상세 사유
6. String benefit // 기대효과
7. String period
8. String returnDate
* */

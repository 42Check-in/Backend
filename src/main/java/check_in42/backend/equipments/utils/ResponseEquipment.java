package check_in42.backend.equipments.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ResponseEquipment {

    private List<EquipmentDTO> equipments;
    private int count;

    public static ResponseEquipment create(List<EquipmentDTO> equipments, int count) {
        ResponseEquipment responseEquipment = new ResponseEquipment();

        responseEquipment.equipments = equipments;
        responseEquipment.count = count;

        return responseEquipment;
    }
}

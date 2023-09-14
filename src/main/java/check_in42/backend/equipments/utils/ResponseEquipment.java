package check_in42.backend.equipments.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ResponseEquipment {

    private List<EquipmentDTO> list;
    private int pageCount;

    public static ResponseEquipment create(List<EquipmentDTO> equipments, int count) {
        ResponseEquipment responseEquipment = new ResponseEquipment();

        responseEquipment.list = equipments;
        responseEquipment.pageCount = count;

        return responseEquipment;
    }
}

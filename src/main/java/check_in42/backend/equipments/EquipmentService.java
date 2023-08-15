package check_in42.backend.equipments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository repository;
    public Long join(Equipment equipment) {
        repository.save(equipment);

        return equipment.getId();
    }

    public Equipment create(EquipmentDTO equipmentDTO) {
        return Equipment.builder()
                .userName(equipmentDTO.getUserName())
                .phoneNumber(equipmentDTO.getPhoneNumber())
                .date(equipmentDTO.getDate())
                .equipment(equipmentDTO.getEquipment())
                .purpose(equipmentDTO.isPurpose())
                .detail(equipmentDTO.getDetail())
                .period(equipmentDTO.getPeriod())
                .benefit(equipmentDTO.getBenefit())
                .returnDate(equipmentDTO.getReturnDate())
                .build();
    }
}

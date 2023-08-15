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

    public Equipment findOne(Long id){
        return repository.findOne(id);
    }

    public void findAndDelete(Long id) {
        Equipment equipment = repository.findOne(id);
        repository.delete(equipment);
    }

    public Equipment create(String intraId, EquipmentDTO equipmentDTO) {
        return Equipment.builder()
                .intraId(intraId)
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

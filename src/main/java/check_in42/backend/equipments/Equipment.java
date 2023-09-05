package check_in42.backend.equipments;

import check_in42.backend.equipments.utils.EquipmentDTO;
import check_in42.backend.equipments.utils.EquipmentType;
import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@Getter
public class Equipment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String intraId;

    private String phoneNumber;

    private LocalDate date;

    // 0으로 들어오면 etc를 받기
    private String purpose; // 1 -> 42과제, 0 -> 그 외

    @Column(length = 1500)
    private String detail;

    @Column(length = 1500)
    private String benefit;

    private int period; // 빌릴 기간

    private LocalDate returnDate; // 반납 일자

    private LocalDateTime approval;

    private boolean notice;

    // 0으로 들어오면 etc를 받기
    private String equipment;

    private int extension;

    private String time;


    @ManyToOne(fetch = FetchType.LAZY) // user쪽에서 casecade 걸어주면 자동으로 추가되게?
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    protected Equipment(User user, EquipmentDTO equipmentDTO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.intraId = user.getIntraId();
        this.userName = equipmentDTO.getUserName();
        this.phoneNumber = equipmentDTO.getPhoneNumber();
        this.purpose = (equipmentDTO.getPurpose() == 0) ?
                equipmentDTO.getEtcPurpose() : "42서울";
        this.detail = equipmentDTO.getDetail();
        this.benefit = equipmentDTO.getBenefit();
        this.date = LocalDate.parse(equipmentDTO.getDate(), formatter);
        this.returnDate = (equipmentDTO.getReturnDate() != null) ?
                LocalDate.parse(equipmentDTO.getReturnDate(), formatter) : null;
        this.period = (equipmentDTO.getPeriod() == 0) ? 1 : 3;
        this.equipment = (equipmentDTO.getEquipment() == 0) ?
                equipmentDTO.getEtcEquipment() : EquipmentType.values()[equipmentDTO.getEquipment()].getName();
        this.user = user;
        this.approval = null;
        this.notice = false;
        this.extension = 0;
        this.time = equipmentDTO.getTime();
    }

    public void extendReturnDateByPeriod(int period) {
        this.returnDate = this.returnDate.plusMonths(period);
    }


    public void updateForExtension(EquipmentDTO equipmentDTO) {
        this.extension = 1;
        this.period = (equipmentDTO.getPeriod() == 0) ? 1 : 3;
        this.date = LocalDate.parse(equipmentDTO.getDate());
        this.time = equipmentDTO.getTime();
        setApprovalNull();
    }

    public void updateForNew(EquipmentDTO equipmentDTO) {
    }

    public void setApproval() {
        this.approval = LocalDateTime.now();
    }
    public void setApprovalNull() {
        this.approval = null;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }
}

package check_in42.backend.equipments;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String intraId;

    private String phoneNumber;

    private LocalDate date;

    private String purpose; // 1 -> 42과제, 0 -> 그 외

    @Column(length = 1500)
    private String detail;

    @Column(length = 1500)
    private String benefit;

    private int period; // 빌릴 기간

    private LocalDate returnDate; // 반납 일자

    private String equipment;

    @ManyToOne(fetch = FetchType.LAZY) // user쪽에서 casecade 걸어주면 자동으로 추가되게?
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    protected Equipment(User user, EquipmentDTO equipmentDTO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.intraId = user.getIntraId();
        this.userName = equipmentDTO.getUserName();
        this.phoneNumber = equipmentDTO.getPhoneNumber();
        if (equipmentDTO.getPurpose() == 1) {
            this.purpose = "42서울";
        } else {
            this.purpose = "그 외" + equipmentDTO.getEtcPurpose();
        }
        this.detail = equipmentDTO.getDetail();
        this.benefit = equipmentDTO.getBenefit();
        this.date = LocalDate.parse(equipmentDTO.getDate(), formatter);
        this.returnDate = LocalDate.parse(equipmentDTO.getReturnDate(), formatter);
        this.period = equipmentDTO.getPeriod();
        if (equipmentDTO.getEquipment() == 0)
            this.equipment = equipmentDTO.getEtcEquipment();
        else
            this.equipment = EquipmentType.values()[equipmentDTO.getEquipment()].getName();
        this.user = user;
    }

    public void extendReturnDateByPeriod(int period) {
        this.returnDate = this.returnDate.plusMonths(period);
    }

    public void setDate(LocalDate extendDate) {
        this.date = extendDate;
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

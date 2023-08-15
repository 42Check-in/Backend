package check_in42.backend.equipments;

import check_in42.backend.equipments.utils.EquipmentType;
import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
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

    private boolean purpose; // 1 -> 42과제, 0 -> 그 외

    private String detail;

    private String benefit;

    private LocalDate period; // 빌릴 기간

    private LocalDate returnDate; // 반납 일자

    @Enumerated(EnumType.STRING)
    private EquipmentType equipment;

    @ManyToOne(fetch = FetchType.LAZY) // user쪽에서 casecade 걸어주면 자동으로 추가되게?
    private User user;

    @Builder
    protected Equipment(String userName, String phoneNumber, String date, int equipment,
                                      boolean purpose, String detail, String benefit, String period, String returnDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.purpose = purpose;
        this.detail = detail;
        this.benefit = benefit;
        this.date = LocalDate.parse(date, formatter);
        this.returnDate = LocalDate.parse(returnDate, formatter);
        this.period = LocalDate.parse(period, formatter);
        this.equipment = EquipmentType.values()[equipment];
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

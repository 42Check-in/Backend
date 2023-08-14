package check_in42.backend.equipments;

import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Equipment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String intraId;

    private String phoneNumber;

    private LocalDateTime date;

    private boolean purpose; // 1 -> 42과제, 0 -> 그 외

    private String detail;

    private String benefit;

    private LocalDateTime period; // 빌릴 기간

    private LocalDateTime returnDate; // 반납 일자

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    protected Equipment(String userName, String phoneNumber, LocalDateTime date, Long equipments,
                                      boolean purpose, String detail, String benefit, LocalDateTime period, LocalDateTime returnDate) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.purpose = purpose;
        this.detail = detail;
        this.period = period;
        this.benefit = benefit;
        this.returnDate = returnDate;

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

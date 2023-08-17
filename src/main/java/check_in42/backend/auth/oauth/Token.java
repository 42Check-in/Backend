package check_in42.backend.auth.oauth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String accessToken;
    private String refreshToken;
    private String UUID;

    public Token(String name, String access, String refresh){
        this.UUID = java.util.UUID.randomUUID().toString();
        this.accessToken = access;
        this.refreshToken = refresh;
        this.userName = name;
    }
}

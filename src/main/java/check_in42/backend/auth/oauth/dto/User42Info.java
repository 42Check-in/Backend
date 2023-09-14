package check_in42.backend.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User42Info {
    private String login;
    @JsonProperty("staff?")
    private boolean staff;

    @JsonProperty("cursus_users")
    private List<CursusUser> cursus_users;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CursusUser {
        @JsonProperty("grade")
        private String grade;

    }
}

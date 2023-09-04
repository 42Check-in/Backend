package check_in42.backend.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User42Info {
    private String login;
    @JsonProperty("staff?")
    private boolean staff;
    private List<CursusUser> cursus_users;

    @Getter
    public static class CursusUser {
        private Cursus cursus;


        @Getter
        public static class Cursus {
            private String grade;

        }
    }
}

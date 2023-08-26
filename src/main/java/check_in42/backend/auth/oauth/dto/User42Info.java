package check_in42.backend.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User42Info {
    private String login;
    @JsonProperty("staff?")
    private boolean staff;
    private Cursus_users cursus_users;
}

package check_in42.backend.auth.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User42Info {
    private String login;
    private boolean staff;
}

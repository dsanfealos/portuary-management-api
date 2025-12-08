package app.portuary_management_api.api.models.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoggedUserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean emailVerified;

    public LoggedUserResponse() {
    }

    public LoggedUserResponse(Long id, String username, String email, String firstName, String lastName, boolean emailVerified) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailVerified = emailVerified;
    }
}

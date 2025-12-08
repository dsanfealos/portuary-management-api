package app.portuary_management_api.api.controllers;

import app.portuary_management_api.api.exception.UserAlreadyExistsException;
import app.portuary_management_api.api.models.auth.LoggedUserResponse;
import app.portuary_management_api.api.models.auth.LoginBody;
import app.portuary_management_api.api.models.auth.LoginResponse;
import app.portuary_management_api.api.models.auth.RegistrationBody;
import app.portuary_management_api.entities.LocalUser;
import app.portuary_management_api.services.LocalUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final LocalUserService localUserService;

    public AuthenticationController(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            if(localUserService.registerUser(registrationBody) == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(UserAlreadyExistsException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginBody loginBody){
        String jwt = localUserService.loginUser(loginBody);
        if (jwt == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LoginResponse response = new LoginResponse();
        response.setJwt(jwt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<LoggedUserResponse> getLoggedUser(@AuthenticationPrincipal LocalUser user){
        LoggedUserResponse response = new LoggedUserResponse(user.getId(), user.getUsername(),
                user.getEmail(), user.getFirstName(), user.getLastName(), user.isEmailVerified());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

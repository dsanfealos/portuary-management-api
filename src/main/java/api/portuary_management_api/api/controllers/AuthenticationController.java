package api.portuary_management_api.api.controllers;

import api.portuary_management_api.api.models.LoginBody;
import api.portuary_management_api.api.models.RegistrationBody;
import api.portuary_management_api.entities.LocalUser;
import api.portuary_management_api.entities.Role;
import api.portuary_management_api.entities.daos.LocalUserDAO;
import api.portuary_management_api.entities.daos.RoleDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final LocalUserDAO localUserDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, LocalUserDAO localUserDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.localUserDAO = localUserDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<LocalUser> register(@RequestBody RegistrationBody registrationBody){
        if (localUserDAO.existsByUsername(registrationBody.getUsername())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setPassword(passwordEncoder.encode(registrationBody.getPassword()));

        Role roles = roleDAO.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        localUserDAO.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginBody loginBody){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                                loginBody.getUsername(), loginBody.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Inicio de sesión correcto.", HttpStatus.OK);
    }

}

package api.portuary_management_api.services;

import api.portuary_management_api.api.exception.UserAlreadyExistsException;
import api.portuary_management_api.api.models.auth.LoginBody;
import api.portuary_management_api.api.models.auth.RegistrationBody;
import api.portuary_management_api.entities.LocalUser;
import api.portuary_management_api.entities.Role;
import api.portuary_management_api.entities.daos.LocalUserDAO;
import api.portuary_management_api.entities.daos.RoleDAO;
import api.portuary_management_api.entities.daos.VerificationTokenDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalUserService {

    private final LocalUserDAO localUserDAO;
    private final VerificationTokenDAO verificationTokenDAO;
    private final JWTService jwtService;
    private final EncryptionService encryptionService;
    private final RoleDAO roleDAO;

    @Value("${localUserService.baseRol}")
    private String BASE_ROL;

    public LocalUserService(LocalUserDAO localUserDAO, VerificationTokenDAO verificationTokenDAO, JWTService jwtService, EncryptionService encryptionService, RoleDAO roleDAO) {
        this.localUserDAO = localUserDAO;
        this.verificationTokenDAO = verificationTokenDAO;
        this.jwtService = jwtService;
        this.encryptionService = encryptionService;
        this.roleDAO = roleDAO;
    }

    public LocalUser registerUser(RegistrationBody body) throws UserAlreadyExistsException {
        if (localUserDAO.existsByUsernameIgnoreCase(body.getUsername()) ||
                localUserDAO.existsByEmailIgnoreCase(body.getEmail())){

            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(body.getEmail());
        user.setUsername(body.getUsername());
        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        user.setPassword(encryptionService.encryptPassword(body.getPassword()));

        Optional<Role> opRole = roleDAO.findByName(BASE_ROL);
        if (opRole.isPresent()){
            user.setRoles(List.of(opRole.get()));
        }else{
            user.setRoles(List.of(addRoleIfNotExists()));
        }


        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody body){
        Optional<LocalUser> opUser = localUserDAO.findByUsername(body.getUsername());
        if (opUser.isPresent() &&
                encryptionService.verifyPassword(body.getPassword(), opUser.get().getPassword())){
            return jwtService.generateJWT(opUser.get());
        }
        return null;
    }

    public Role addRoleIfNotExists(){
        Role role = new Role();
        role.setName(BASE_ROL);
        return roleDAO.save(role);
    }
}

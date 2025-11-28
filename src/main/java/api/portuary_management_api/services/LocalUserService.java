package api.portuary_management_api.services;

import api.portuary_management_api.entities.LocalUser;
import api.portuary_management_api.entities.Role;
import api.portuary_management_api.entities.daos.LocalUserDAO;
import api.portuary_management_api.entities.daos.RoleDAO;
import api.portuary_management_api.entities.daos.VerificationTokenDAO;
import org.springframework.stereotype.Service;

@Service
public class LocalUserService {

    private final LocalUserDAO localUserDAO;
    private final VerificationTokenDAO verificationTokenDAO;
    private final JWTService jwtService;
    private final EncryptionService encryptionService;
    private final RoleDAO roleDAO;

    public LocalUserService(LocalUserDAO localUserDAO, VerificationTokenDAO verificationTokenDAO, JWTService jwtService, EncryptionService encryptionService, RoleDAO roleDAO) {
        this.localUserDAO = localUserDAO;
        this.verificationTokenDAO = verificationTokenDAO;
        this.jwtService = jwtService;
        this.encryptionService = encryptionService;
        this.roleDAO = roleDAO;
    }

    public LocalUser registerUser(){
        return null;
    }

    public Role addRoleIfNotExists(){
        Role role = new Role();
        role.setName("CAPTAIN");
        return roleDAO.save(role);
    }
}

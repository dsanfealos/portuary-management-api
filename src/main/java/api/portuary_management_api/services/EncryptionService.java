package api.portuary_management_api.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;

    @PostConstruct
    public void postConstruct(){
        salt = BCrypt.gensalt(saltRounds);
    }

    public String encryptPassword(String password){
        return BCrypt.hashpw(password, salt);
    }

    public boolean verifyPassword(String password, String hash){
        //password is the normally typed password, and hash is the
        //hashed password at DB (accessed like user.getPassword)
        return BCrypt.checkpw(password, hash);
    }

}

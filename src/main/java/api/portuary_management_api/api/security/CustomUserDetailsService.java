package api.portuary_management_api.api.security;

import api.portuary_management_api.entities.LocalUser;
import api.portuary_management_api.entities.Role;
import api.portuary_management_api.entities.daos.LocalUserDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LocalUserDAO localUserDAO;

    public CustomUserDetailsService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LocalUser user = localUserDAO
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("This user does not exists."));

        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

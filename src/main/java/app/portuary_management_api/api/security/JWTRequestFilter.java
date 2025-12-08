package app.portuary_management_api.api.security;

import app.portuary_management_api.entities.LocalUser;
import app.portuary_management_api.entities.Role;
import app.portuary_management_api.entities.daos.LocalUserDAO;
import app.portuary_management_api.entities.daos.RoleDAO;
import app.portuary_management_api.services.JWTService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final LocalUserDAO localUserDAO;
    private final RoleDAO roleDAO;

    public JWTRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO, RoleDAO roleDAO) {
        this.jwtService = jwtService;
        this.localUserDAO = localUserDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<LocalUser> opUser = localUserDAO.findByUsername(username);
                if (opUser.isPresent()){
                    LocalUser user = opUser.get();

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user, null, mapRolesToGrantedAuthorities(user));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch(JWTDecodeException e){
                System.err.println("The JWT is incorrect or false.");
            }
        }

        filterChain.doFilter(request, response);
    }

    private Collection<GrantedAuthority> mapRolesToGrantedAuthorities(LocalUser user){

        List<Role> roles = roleDAO.findByUsers_Username(user.getUsername());
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

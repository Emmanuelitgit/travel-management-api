package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_management_system.Components.JWTAccess;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.User;
import travel_management_system.Response.ResponseHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTAccess jwtAccess;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTAccess jwtAccess) {
        this.authenticationManager = authenticationManager;
        this.jwtAccess = jwtAccess;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticateUser(@RequestBody User user){
        log.info("In authentication method:=====================");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        if (!authentication.isAuthenticated()){
            throw new  NotFoundException("user not authenticated");
        }

        String token = jwtAccess.generateToken(user.getEmail());
        Map<String, Object> authenticatedData = new HashMap<>();
        authenticatedData.put("user", authentication.getName());
        authenticatedData.put("role(s)", authentication.getAuthorities());
        authenticatedData.put("authenticated", authentication.isAuthenticated());
        authenticatedData.put("token", token);
        return ResponseHandler.responseBuilder("User authenticated successfully", authenticatedData, HttpStatus.OK);
    }
}
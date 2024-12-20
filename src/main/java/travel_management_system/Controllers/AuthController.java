package travel_management_system.Controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import travel_management_system.Components.JWTAccess;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.User;
import travel_management_system.Response.ResponseHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTAccess jwtAccess;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTAccess jwtAccess) {
        this.authenticationManager = authenticationManager;
        this.jwtAccess = jwtAccess;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticateUser(@RequestBody User user, HttpSession session){
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
        System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));
        return ResponseHandler.responseBuilder("User authenticated successfully", authenticatedData, HttpStatus.OK);
    }


    // Create a session and store an attribute
    @GetMapping("/create")
    public String createSession(HttpSession session) {
        session.setAttribute("username", "JohnDoe");
        String sessionId = session.getId();
        return "Session created with ID: " + sessionId;
    }

    // Retrieve session attribute
    @GetMapping("/get")
    public String getSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "No session found!";
        }
        return "Session found with username: " + username;
    }
}
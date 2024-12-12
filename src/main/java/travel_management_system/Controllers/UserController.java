package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_management_system.Models.User;
import travel_management_system.Response.ResponseHandler;
import travel_management_system.Services.UserService;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // endpoint for creating new user
    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        log.info("in creating user controller==========");
        return userService.createUser(user);
    }

    // endpoint for fetching users from db
    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        log.info("in fetching users controller========");
        List<User> users =  userService.getUsers();
        return ResponseHandler.responseBuilder("users details", users, HttpStatus.OK);
    }
}

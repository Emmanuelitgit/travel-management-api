package travel_management_system.Controllers.RestApis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_management_system.DTO.UserDTO;
import travel_management_system.Models.User;
import travel_management_system.Response.ResponseHandler;
import travel_management_system.Services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // endpoint for creating new user
    @PostMapping("/create-user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        log.info("in creating user controller==========");
        UserDTO userDTO = userService.createUser(user);
        return ResponseHandler.responseBuilder("user created successfully", userDTO, HttpStatus.CREATED);
    }

    // endpoint for fetching users from db
    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        log.info("in fetching users controller========");
        List<UserDTO> users =  userService.getUsers();
        return ResponseHandler.responseBuilder("users details", users, HttpStatus.OK);
    }

    // an endpoint for fetching all users with pagination feature
    @GetMapping("/users-pagination")
    public ResponseEntity<Object> getUsersByPage(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "4") int size,
                                                 @RequestParam(defaultValue = "id") String id,
                                                 @RequestParam(defaultValue = "true") boolean ascending){
        List<UserDTO> users = userService.getUsersByPage(page, size, id, ascending);
        return ResponseHandler.responseBuilder("users details on pagination", users, HttpStatus.OK);
    }

    // an endpoint to get user by id
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId){
        UserDTO user = userService.getUserById(userId);
        return ResponseHandler.responseBuilder("user details", user, HttpStatus.OK);
    }
}
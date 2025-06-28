package travel_management_system.Controllers.WebApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel_management_system.DTO.UserDTO;
import travel_management_system.Models.User;
import travel_management_system.Repositories.UserRepository;
import travel_management_system.Services.UserService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller("userWebController")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String getLoginPage(Model model){
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("users", users);
        return "home";
    }

    @GetMapping("/add-user")
    public String getAddUserPage(Model model){
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute("user") User user, Model model){
        Date date = new Date(2024-12-1);
        user.setStartDate(date);
        if (user == null){
            throw new NullPointerException("user cannot be empty");
        }
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/update-user/{id}")
    public String getUpdateUserPage(@PathVariable Long id, Model model){
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/update-user/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable Long id,  Model model){
        Optional<User>userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new NullPointerException("No user found");
        }
        User existingData = userOptional.get();
        existingData.setEmail(user.getEmail());
        existingData.setName(user.getName());
        existingData.setStartDate(user.getStartDate());
        existingData.setRole(user.getRole());
        userService.createUser(existingData);
        return "redirect:/users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id){
        Optional<User>userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new NullPointerException("No user found");
        }
        userRepository.delete(userOptional.get());
        return "redirect:/users";
    }
}

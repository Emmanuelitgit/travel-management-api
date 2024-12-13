package travel_management_system.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel_management_system.DTO.UserDTO;
import travel_management_system.DTO.UserDTOMapper;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.User;
import travel_management_system.Repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // a method to create new user
    public UserDTO createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
         return UserDTOMapper.toDTO(user);
    }

    // a method to fetch users from db
    public List<UserDTO> getUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new NotFoundException("No user data found");
        }
        return UserDTOMapper.userDTOList(users);
    }
}

package travel_management_system.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel_management_system.Models.User;
import travel_management_system.Repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // a method to create new user
    public User createUser(User user){
        log.info("user payload==== {}", user);
            return userRepository.save(user);
    }

    // a method to fetch users from db
    public List<User> getUsers(){
        return userRepository.findAll();
    }
}

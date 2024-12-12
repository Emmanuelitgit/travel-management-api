package travel_management_system.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.User;
import travel_management_system.Repositories.UserRepository;

import java.util.Optional;

@Configuration
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usernameFromDatabase  = userRepository.findUserByEmail(username);

        if (usernameFromDatabase.isPresent()){
            var userObject = usernameFromDatabase.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObject.getEmail())
                    .password(userObject.getPassword())
                    .roles(getRoles(userObject))
                    .build();
        }
        throw new UsernameNotFoundException("username not found");
    }

    public String[] getRoles(User user){
        if (user.getRole().isEmpty()){
            return new String[]{"employee"};
        }
        return user.getRole().split(",");
    }
}

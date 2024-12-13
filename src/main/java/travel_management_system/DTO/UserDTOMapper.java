package travel_management_system.DTO;

import org.springframework.stereotype.Component;
import travel_management_system.Models.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOMapper {

    public static UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStartDate()
        );
    }

    public static List<UserDTO> userDTOList(List<User> users){
        return users.stream()
                .map(UserDTOMapper::toDTO)
                .collect(Collectors.toList());
    }
}

package travel_management_system.DTOMappers;

import org.springframework.stereotype.Component;
import travel_management_system.DTO.LeaveRequestDTO;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class LeaveRequestMapper {

    public static LeaveRequestDTO toDTO(LeaveRequest leaveRequest){

        HashMap<String, Object> user = new HashMap<>();
        user.put("id", leaveRequest.getUser().getId());
        user.put("name", leaveRequest.getUser().getName());
        user.put("email", leaveRequest.getUser().getEmail());
        user.put("role", leaveRequest.getUser().getRole());
        user.put("startDate", leaveRequest.getUser().getStartDate());

        return new LeaveRequestDTO(
                leaveRequest.getId(),
                leaveRequest.getDeparture_date(),
                leaveRequest.getArrival_date(),
                leaveRequest.getLeave_days(),
                leaveRequest.getStatus(),
                user
        );
    }

    public List<LeaveRequestDTO> leaveRequestDTOList(List<LeaveRequest> leaveRequestList){
        return leaveRequestList.stream()
                .map(LeaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

}

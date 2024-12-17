package travel_management_system.DTOMappers;

import org.springframework.stereotype.Component;
import travel_management_system.DTO.FlightAndLeaveBalanceDTO;
import travel_management_system.Models.FlightAndLeaveBalance;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightAndLeaveBalanceMapper {

    public static FlightAndLeaveBalanceDTO toDTO(FlightAndLeaveBalance flightAndLeaveBalance){

        HashMap<String, Object> user  = new HashMap<>();
        user.put("id", flightAndLeaveBalance.getUser().getId());
        user.put("name", flightAndLeaveBalance.getUser().getName());
        user.put("email", flightAndLeaveBalance.getUser().getEmail());
        user.put("role", flightAndLeaveBalance.getUser().getRole());
        user.put("startDate", flightAndLeaveBalance.getUser().getStartDate());

        return new FlightAndLeaveBalanceDTO(
                flightAndLeaveBalance.getId(),
                flightAndLeaveBalance.getFlight_balance(),
                flightAndLeaveBalance.getLeave_balance(),
                flightAndLeaveBalance.getAccumulated_working_days(),
                user
        );
    }

    public List<FlightAndLeaveBalanceDTO> flightAndLeaveBalanceDTOList(List<FlightAndLeaveBalance> flightAndLeaveBalances){
        return flightAndLeaveBalances.stream()
                .map(FlightAndLeaveBalanceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
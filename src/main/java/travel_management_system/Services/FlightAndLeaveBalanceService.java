package travel_management_system.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Models.User;
import travel_management_system.Repositories.UserRepository;

@Slf4j
@Service
public class FlightAndLeaveBalanceService {

    private final travel_management_system.Repositories.FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository;
    private final UserRepository userRepository;

    public FlightAndLeaveBalanceService(travel_management_system.Repositories.FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository, UserRepository userRepository) {
        this.flightAndLeaveBalanceRepository = flightAndLeaveBalanceRepository;
        this.userRepository = userRepository;
    }

    public FlightAndLeaveBalance addUserFlightAndLeaveBalance(FlightAndLeaveBalance flightAndLeaveBalance, Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            throw new NotFoundException("User not found");
        }
        if (flightAndLeaveBalance == null){
            throw new NotFoundException("No data to be added");
        }
        flightAndLeaveBalance.setUser(user);
        user.setFlightAndLeaveBalance(flightAndLeaveBalance);
        log.info("flight balance payload:{}", String.valueOf(flightAndLeaveBalance));
        return flightAndLeaveBalanceRepository.save(flightAndLeaveBalance);
    }
}

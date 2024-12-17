package travel_management_system.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel_management_system.DTO.FlightAndLeaveBalanceDTO;
import travel_management_system.DTOMappers.FlightAndLeaveBalanceMapper;
import travel_management_system.Exception.NotFoundException;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Models.User;
import travel_management_system.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FlightAndLeaveBalanceService {

    private final travel_management_system.Repositories.FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository;
    private final UserRepository userRepository;
    private final FlightAndLeaveBalanceMapper flightAndLeaveBalanceMapper;

    public FlightAndLeaveBalanceService(travel_management_system.Repositories.FlightAndLeaveBalanceRepository flightAndLeaveBalanceRepository, UserRepository userRepository, FlightAndLeaveBalanceMapper flightAndLeaveBalanceMapper) {
        this.flightAndLeaveBalanceRepository = flightAndLeaveBalanceRepository;
        this.userRepository = userRepository;
        this.flightAndLeaveBalanceMapper = flightAndLeaveBalanceMapper;
    }

    // a method to add an existing user flight and leave balance
    public FlightAndLeaveBalanceDTO addUserFlightAndLeaveBalance(FlightAndLeaveBalance flightAndLeaveBalance, Long userId){
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
        flightAndLeaveBalanceRepository.save(flightAndLeaveBalance);
        return FlightAndLeaveBalanceMapper.toDTO(flightAndLeaveBalance);
    }

    // a method to get flight and leave balance for all users
    public List<FlightAndLeaveBalanceDTO> getFlightAndLeaveBalanceList(){
        List<FlightAndLeaveBalance> flightAndLeaveBalances = flightAndLeaveBalanceRepository.findAll();
        if (flightAndLeaveBalances.isEmpty()){
            throw new NotFoundException("no flight and leave balance data found");
        }
        return flightAndLeaveBalanceMapper.flightAndLeaveBalanceDTOList(flightAndLeaveBalances);
    }

    // a method to get a flight and leave balance by id
    public FlightAndLeaveBalanceDTO getFlightAndLeaveBalance(Long userId){
        FlightAndLeaveBalance flightAndLeaveBalance = flightAndLeaveBalanceRepository.findById(userId).orElse(null);
        if (flightAndLeaveBalance == null){
            throw new NotFoundException("no flight and leave balance data found");
        }
        return FlightAndLeaveBalanceMapper.toDTO(flightAndLeaveBalance);
    }

    // a method to get flight and leave balance by user id
    public FlightAndLeaveBalanceDTO getFlightAndLeaveBalanceByUserId(Long userId){
        Optional<FlightAndLeaveBalance> flightAndLeaveBalance = flightAndLeaveBalanceRepository.findFlightAndLeaveBalanceByUserId(userId);
        if (flightAndLeaveBalance.isEmpty()){
            throw new NotFoundException("user flight and leave balance not found");
        }
        return FlightAndLeaveBalanceMapper.toDTO(flightAndLeaveBalance.get());
    }
}

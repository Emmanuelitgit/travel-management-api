package travel_management_system.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import travel_management_system.Components.CalculateFlighAndLeaveBalanceMethods;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Models.User;
import travel_management_system.Repositories.LeaveRequestRepository;
import travel_management_system.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;
    private final CalculateFlighAndLeaveBalanceMethods calculateFlighAndLeaveBalanceMethods;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository, CalculateFlighAndLeaveBalanceMethods calculateFlighAndLeaveBalanceMethods) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
        this.calculateFlighAndLeaveBalanceMethods = calculateFlighAndLeaveBalanceMethods;
    }

    // a method for adding leave request to the db
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest, Long user_id){
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()){
            Long leave_days = calculateFlighAndLeaveBalanceMethods.calculateLeaveRequestingDays(
                    leaveRequest.getDeparture_date(), leaveRequest.getArrival_date()
            );
            User user = userOptional.get();
            leaveRequest.setUser(user);
            leaveRequest.setLeave_days(leave_days);
            user.getLeaveRequests().add(leaveRequest);
            leaveRequestRepository.save(leaveRequest);
            return leaveRequest;
        }
        else {
            throw new NullPointerException();
        }
    }

    // a method for leave request approval
    public LeaveRequest approveLeaveRequest(Long leave_request_id){
        double leaveBalance = calculateFlighAndLeaveBalanceMethods.calculateLeaveBalance(leave_request_id);
        double flightBalance = calculateFlighAndLeaveBalanceMethods.calculateFlightBalance(leave_request_id);
//        log.info("flight:========={} Leave: {}", flightBalance, leaveBalnce);
        LeaveRequest request = leaveRequestRepository.findById(leave_request_id).orElse(null);
//        request.setStatus(true);
//        leaveRequestRepository.save(request);
        return request;
    }
}
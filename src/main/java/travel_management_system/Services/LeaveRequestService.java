package travel_management_system.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Models.User;
import travel_management_system.Repositories.LeaveRequestRepository;
import travel_management_system.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
    }

    // a method for adding leave request to the db
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest, Long user_id){
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            leaveRequest.setUser(user);
            user.getLeaveRequests().add(leaveRequest);
            leaveRequestRepository.save(leaveRequest);
            return leaveRequest;
        }
        else {
            throw new NullPointerException();
        }
    }
}

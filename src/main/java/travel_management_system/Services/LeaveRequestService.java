package travel_management_system.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel_management_system.Components.CalculateFlightAndLeaveBalanceMethods;
import travel_management_system.Components.MailSenderComponent;
import travel_management_system.DTO.LeaveRequestDTO;
import travel_management_system.DTOMappers.LeaveRequestMapper;
import travel_management_system.Exception.NotFoundException;
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
    private final CalculateFlightAndLeaveBalanceMethods calculateFlightAndLeaveBalanceMethods;
    private final MailSenderComponent mailSenderComponent;
    private final LeaveRequestMapper leaveRequestMapper;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository, CalculateFlightAndLeaveBalanceMethods calculateFlightAndLeaveBalanceMethods, MailSenderComponent mailSenderComponent, LeaveRequestMapper leaveRequestMapper) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
        this.calculateFlightAndLeaveBalanceMethods = calculateFlightAndLeaveBalanceMethods;
        this.mailSenderComponent = mailSenderComponent;
        this.leaveRequestMapper = leaveRequestMapper;
    }

    // a method for adding leave request to the db
    public LeaveRequestDTO createLeaveRequest(LeaveRequest leaveRequest, Long user_id){
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()){
            Long leave_days = calculateFlightAndLeaveBalanceMethods.calculateLeaveRequestingDays(
                    leaveRequest.getDeparture_date(), leaveRequest.getArrival_date()
            );
            User user = userOptional.get();
            leaveRequest.setUser(user);
            leaveRequest.setLeave_days(leave_days);
            user.getLeaveRequests().add(leaveRequest);
            leaveRequestRepository.save(leaveRequest);
            mailSenderComponent.sendLeaveRequestMail(user.getEmail(), user.getName());
            return LeaveRequestMapper.toDTO(leaveRequest);
        }
        else {
            throw new NullPointerException();
        }
    }

    // get all leave requests
    public List<LeaveRequestDTO> getLeaveRequestList(){
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
        if (leaveRequestList.isEmpty()){
            throw new NotFoundException("No leave request data found");
        }
        return leaveRequestMapper.leaveRequestDTOList(leaveRequestList);
    }

    // a method to get a leave request by id
    public LeaveRequestDTO getLeaveRequest(Long leaveRequestId){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElse(null);
        if (leaveRequest == null){
            throw new NotFoundException("No leave request information found");
        }
        return LeaveRequestMapper.toDTO(leaveRequest);
    }

    // a method to get leave request by user id
    public LeaveRequestDTO getLeaveRequestByUserId(Long userId){
        Optional<LeaveRequest > leaveRequest = leaveRequestRepository.findLeaveRequestByUserId(userId);
        if (leaveRequest.isEmpty()){
            throw new NotFoundException("user data not found");
        }
        LeaveRequest leaveRequestData = leaveRequest.get();
        return LeaveRequestMapper.toDTO(leaveRequestData);
    }

    // a method for leave request approval
    public LeaveRequestDTO approveLeaveRequest(Long leaveRequestId){
        FlightAndLeaveBalance leaveBalance = calculateFlightAndLeaveBalanceMethods.calculateFlightAndLeaveBalance(leaveRequestId);
        LeaveRequest request = leaveRequestRepository.findById(leaveRequestId).orElse(null);
        if (request == null){
            throw new NotFoundException("leave request not found");
        }
        request.setStatus(true);
        leaveRequestRepository.save(request);
        return LeaveRequestMapper.toDTO(request);
    }
}
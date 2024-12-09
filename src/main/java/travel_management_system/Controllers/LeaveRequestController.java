package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_management_system.Components.CalculateFlightAndLeaveBalanceMethods;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Response.ResponseHandler;
import travel_management_system.Services.LeaveRequestService;
import travel_management_system.Services.UserService;

import java.util.List;

@Slf4j
@RestController
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;
    private final UserService userService;
    private final CalculateFlightAndLeaveBalanceMethods calculateFlightAndLeaveBalanceMethods;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService, UserService userService, CalculateFlightAndLeaveBalanceMethods calculateFlightAndLeaveBalanceMethods) {
        this.leaveRequestService = leaveRequestService;
        this.userService = userService;
        this.calculateFlightAndLeaveBalanceMethods = calculateFlightAndLeaveBalanceMethods;
    }

    // endpoint for adding leave request
    @PostMapping("/create-leave-request/{user_id}")
    public LeaveRequest createLeaveRequest(@RequestBody LeaveRequest leaveRequest, @PathVariable Long user_id){
        log.info("in leave request controller=========");
        return leaveRequestService.createLeaveRequest(leaveRequest, user_id);
    }

    // endpoint for retrieving all leave request
    @GetMapping("/leave-request-list")
    public ResponseEntity<Object> getLeaveRequestList(){
        List<LeaveRequest> leaveRequestList = leaveRequestService.getLeaveRequestList();
        log.info("In leave request list");
        return ResponseHandler.responseBuilder("Leave request details", leaveRequestList, HttpStatus.OK );
    }

    // endpoint for approving leave request
    @GetMapping("/approve/{leave_request_id}")
    public LeaveRequest approveLeaveRequest(@PathVariable Long leave_request_id){
        return leaveRequestService.approveLeaveRequest(leave_request_id);
    }
}
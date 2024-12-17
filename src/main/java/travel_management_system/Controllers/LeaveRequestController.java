package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_management_system.Components.CalculateFlightAndLeaveBalanceMethods;
import travel_management_system.DTO.LeaveRequestDTO;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Response.ResponseHandler;
import travel_management_system.Services.LeaveRequestService;
import travel_management_system.Services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
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
    public ResponseEntity<Object> createLeaveRequest(@RequestBody LeaveRequest leaveRequest, @PathVariable Long user_id){
        log.info("in leave request controller=========");
        LeaveRequestDTO leaveRequestDTO = leaveRequestService.createLeaveRequest(leaveRequest, user_id);
        return ResponseHandler.responseBuilder("leave request created successfully", leaveRequestDTO, HttpStatus.OK);
    }

    // endpoint for retrieving all leave request
    @GetMapping("/leave-request-list")
    public ResponseEntity<Object> getLeaveRequestList(){
        List<LeaveRequestDTO> leaveRequestList = leaveRequestService.getLeaveRequestList();
        log.info("In leave request list");
        return ResponseHandler.responseBuilder("Leave request details", leaveRequestList, HttpStatus.OK );
    }

    // endpoint for retrieving leave request by id
    @GetMapping("leave-request/{leaveRequestId}")
    public ResponseEntity<Object> getLeaveRequest(@PathVariable Long leaveRequestId){
        LeaveRequestDTO leaveRequest = leaveRequestService.getLeaveRequest(leaveRequestId);
        return  ResponseHandler.responseBuilder("leave request details", leaveRequest, HttpStatus.OK);
    }

    // an endpoint to get leave request by user id
    @GetMapping("/leave-request-by-userId/{userId}")
    public ResponseEntity<Object> getLeaveRequestByUserId(@PathVariable Long userId){
        LeaveRequestDTO leaveRequestDTO = leaveRequestService.getLeaveRequestByUserId(userId);
        return ResponseHandler.responseBuilder("user leave request details", leaveRequestDTO, HttpStatus.OK);
    }

    // endpoint for approving leave request
    @GetMapping("/approve/{leave_request_id}")
    public ResponseEntity<Object> approveLeaveRequest(@PathVariable Long leave_request_id){
        LeaveRequestDTO leaveRequest = leaveRequestService.approveLeaveRequest(leave_request_id);
        return ResponseHandler.responseBuilder("leave request approved successfully", leaveRequest, HttpStatus.OK);
    }
}
package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_management_system.DTO.FlightAndLeaveBalanceDTO;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Response.ResponseHandler;
import travel_management_system.Services.FlightAndLeaveBalanceService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class FlightAndLeaveBalanceController {

    private final FlightAndLeaveBalanceService flightAndLeaveBalanceService;

    @Autowired
    public FlightAndLeaveBalanceController(FlightAndLeaveBalanceService flightAndLeaveBalanceService) {
        this.flightAndLeaveBalanceService = flightAndLeaveBalanceService;
    }

    // an endpoint to add flight and leave balance for an existing user
    @PostMapping("/add-user-balance/{userId}")
    public ResponseEntity<Object> addUserFlightAndLeaveBalance(@RequestBody FlightAndLeaveBalance flightAndLeaveBalance, @PathVariable Long userId){
        log.info("In add user balance method: ====================={}", flightAndLeaveBalance);
        FlightAndLeaveBalanceDTO flightAndLeaveBalanceDTO = flightAndLeaveBalanceService.addUserFlightAndLeaveBalance(flightAndLeaveBalance, userId);
        return ResponseHandler.responseBuilder("user flight and balance added successfully", flightAndLeaveBalanceDTO, HttpStatus.OK);
    }

    // an endpoint to flight and leave balance for all users
    @GetMapping("/flight-leave-balance-list")
    public ResponseEntity<Object> getFlightAndLeaveBalanceList(){
        List<FlightAndLeaveBalanceDTO> flightAndLeaveBalance = flightAndLeaveBalanceService.getFlightAndLeaveBalanceList();
        return ResponseHandler.responseBuilder("flight and leave balance details", flightAndLeaveBalance, HttpStatus.OK);
    }

    // an endpoint to retrieve flight and leave balance by id
    @GetMapping("/flight-leave-balance/{leaveRequestId}")
    public ResponseEntity<Object> getFlightAndLeaveBalance(@PathVariable Long leaveRequestId) {
        FlightAndLeaveBalanceDTO flightAndLeaveBalance = flightAndLeaveBalanceService.getFlightAndLeaveBalance(leaveRequestId);
        return ResponseHandler.responseBuilder("flight and balance details", flightAndLeaveBalance, HttpStatus.OK);
    }

    // an endpoint to retrieve flight and leave balance by user id
    @GetMapping("/flight-leave-balance-by-userId/{userId}")
    public ResponseEntity<Object> getFlightAndLeaveBalanceByUserId(@PathVariable Long userId){
        FlightAndLeaveBalanceDTO flightAndLeaveBalanceDTO = flightAndLeaveBalanceService.getFlightAndLeaveBalanceByUserId(userId);
        return ResponseHandler.responseBuilder("user flight and leave balance details", flightAndLeaveBalanceDTO, HttpStatus.OK);
    }
}
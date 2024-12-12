package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_management_system.Models.FlightAndLeaveBalance;
import travel_management_system.Services.FlightAndLeaveBalanceService;

@Slf4j
@RestController
public class FlightAndLeaveBalanceController {

    private final FlightAndLeaveBalanceService flightAndLeaveBalanceService;

    @Autowired
    public FlightAndLeaveBalanceController(FlightAndLeaveBalanceService flightAndLeaveBalanceService) {
        this.flightAndLeaveBalanceService = flightAndLeaveBalanceService;
    }

    @PostMapping("/add-user-balance/{userId}")
    public FlightAndLeaveBalance addUserFlightAndLeaveBalance(@RequestBody FlightAndLeaveBalance flightAndLeaveBalance, @PathVariable Long userId){
        log.info("In add user balance method: ====================={}", flightAndLeaveBalance);
        return flightAndLeaveBalanceService.addUserFlightAndLeaveBalance(flightAndLeaveBalance, userId);
    }
}
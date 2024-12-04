package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_management_system.Models.LeaveRequest;
import travel_management_system.Services.LeaveRequestService;

@Slf4j
@RestController
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // endpoint for adding leave request
    @PostMapping("/create-leave-request/{user_id}")
    public LeaveRequest createLeaveRequest(@RequestBody LeaveRequest leaveRequest, @PathVariable Long user_id){
        log.info("in leave request controller=========");
        return leaveRequestService.createLeaveRequest(leaveRequest, user_id);
    }
}

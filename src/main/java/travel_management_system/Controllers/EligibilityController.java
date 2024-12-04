package travel_management_system.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_management_system.Models.Eligibility;
import travel_management_system.Services.EligibilityService;

@Slf4j
@RestController
public class EligibilityController {

    private final EligibilityService eligibilityService;
    @Autowired
    public EligibilityController(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    // endpoint for creating new eligibility level
    @PostMapping("create-eligibility")
    public Eligibility createEligibility(@RequestBody Eligibility eligibility){
        log.info("in creating eligibility controller=========");
        return eligibilityService.createEligibility(eligibility);
    }
}

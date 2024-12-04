package travel_management_system.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel_management_system.Models.Eligibility;
import travel_management_system.Repositories.EligibilityRepository;

@Service
public class EligibilityService {

    private final EligibilityRepository eligibilityRepository;

    @Autowired
    public EligibilityService(EligibilityRepository eligibilityRepository) {
        this.eligibilityRepository = eligibilityRepository;
    }

    public Eligibility createEligibility(Eligibility eligibility){
        return eligibilityRepository.save(eligibility);
    }
}

package travel_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import travel_management_system.Models.Eligibility;

public interface EligibilityRepository extends JpaRepository<Eligibility, Long> {
}

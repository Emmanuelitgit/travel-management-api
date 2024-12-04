package travel_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import travel_management_system.Models.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}

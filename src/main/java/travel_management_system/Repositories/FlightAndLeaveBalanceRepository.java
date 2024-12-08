package travel_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightAndLeaveBalanceRepository extends JpaRepository<travel_management_system.Models.FlightAndLeaveBalance, Long> {
}

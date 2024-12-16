package travel_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import travel_management_system.Models.LeaveRequest;

import java.sql.Date;
import java.util.Optional;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("SELECT MAX(request.arrival_date) FROM LeaveRequest request WHERE request.user.id = :userId AND request.status = true")
    Optional<Date> getUserLatestArrivalByUserId(Long userId);
}

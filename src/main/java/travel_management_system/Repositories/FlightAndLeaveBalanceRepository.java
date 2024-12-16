package travel_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import travel_management_system.Models.FlightAndLeaveBalance;

import java.util.Optional;

@Repository
public interface FlightAndLeaveBalanceRepository extends JpaRepository<travel_management_system.Models.FlightAndLeaveBalance, Long> {

    @Query("SELECT flb FROM FlightAndLeaveBalance flb WHERE flb.user.id = ?1")
    Optional<FlightAndLeaveBalance> findFlightAndLeaveBalanceByUser_Id(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE FlightAndLeaveBalance balance SET balance.accumulated_working_days = balance.accumulated_working_days + 1")
    public void calculateAccumulatedLeaveDays();
}

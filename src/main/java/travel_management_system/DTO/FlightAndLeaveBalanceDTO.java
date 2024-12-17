package travel_management_system.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import travel_management_system.Models.User;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FlightAndLeaveBalanceDTO {
    private Long id;
    private double flight_balance;
    private double leave_balance;
    private long accumulated_working_days = 0;
    private HashMap<String, Object> user;
}
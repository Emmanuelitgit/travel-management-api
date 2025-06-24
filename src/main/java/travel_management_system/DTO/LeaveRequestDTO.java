package travel_management_system.DTO;

import jakarta.persistence.Column;
import lombok.*;
import travel_management_system.Models.User;

import java.sql.Date;
import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LeaveRequestDTO {
    private Long id;
    private Date departure_date;
    private Date arrival_date;
    private double leave_days;
    private String status;
    HashMap<String, Object> user;
}

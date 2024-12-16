package travel_management_system.Models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leave_request_tb")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    public Date departure_date;
    @Column
    public Date arrival_date;
    @Column
    public double leave_days;
    @Column
    public boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}

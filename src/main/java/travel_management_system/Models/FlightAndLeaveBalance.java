package travel_management_system.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight_and_leave_balance_tb")
public class FlightAndLeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long id;
    @Column
    public int flight_balance;
    @Column
    public int leave_balance;
    @Column
    public long accumulated_working_days = 0;
    @JoinColumn(name = "user_id")
    @OneToOne
    public User user;
}

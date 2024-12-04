package travel_management_system.Models;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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
    public int leave_days;
    @Column
    public boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User user;
}

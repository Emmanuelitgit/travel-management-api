package travel_management_system.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
public class User {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    public String name;
    @Column
    public String email;
    @Column
    public String password;
    @Column
    public String role;
    @Column
    public Date startDate;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    public List<LeaveRequest> leaveRequests;
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    FlightAndLeaveBalance flightAndLeaveBalance;

    public User(Long id, String name, String email, String password, String role, Date startDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.startDate = startDate;
    }

}

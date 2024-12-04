package travel_management_system.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "eligibility_tb")
public class Eligibility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    public String eligibility_type;
    @Column
    public int max_working_days;
    @Column
    public int flight;
    @Column
    public int max_leave_days;
}

package travel_management_system.DTO;

import jakarta.persistence.Column;

import java.sql.Date;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Date startDate;

    public UserDTO(Long id, String name, String email, String role, Date startDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}

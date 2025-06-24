package travel_management_system.Configurations.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMSUpdatePayload {
    private String requestType;
    private Long requestedBy;
    private Long leaveId;
    private String ApplicationType;
    private String approveleaverequest;
    private String status;
}

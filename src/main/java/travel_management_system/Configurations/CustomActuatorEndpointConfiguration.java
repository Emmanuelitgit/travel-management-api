package travel_management_system.Configurations;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "customEndpoint")
public class CustomActuatorEndpointConfiguration {

    @ReadOperation
    public String customEndpoint() {
        return "Hello from custom endpoint!";
    }

    @WriteOperation
    public String writeOperation(String name) {
        return "Hello, " + name;
    }

    @DeleteOperation
    public String deleteOperation() {
        return "Delete operation performed";
    }
}

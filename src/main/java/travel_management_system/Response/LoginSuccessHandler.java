package travel_management_system.Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);

        boolean isUserAdmin  = authentication.getAuthorities().stream()
                .anyMatch(data->{
                    data.getAuthority().equals("Role_admin");
                    return true;
                });

        boolean isUserEmployee  = authentication.getAuthorities().stream()
                .anyMatch(data->{
                    data.getAuthority().equals("Role_employee");
                    return true;
                });
        if (isUserAdmin){
            setDefaultTargetUrl("/users");
        } else if (isUserEmployee) {
            setDefaultTargetUrl("/");
        } else {
            setDefaultTargetUrl("/login");
        }
    }
}
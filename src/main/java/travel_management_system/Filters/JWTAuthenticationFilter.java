package travel_management_system.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import travel_management_system.Components.JWTAccess;
import travel_management_system.Components.UserDetailService;

import java.io.IOException;
import java.net.CookieManager;
import java.util.Arrays;

@Configuration
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTAccess jwtAccess;
    private final UserDetailService userDetailService;

    @Autowired
    public JWTAuthenticationFilter(JWTAccess jwtAccess, UserDetailService userDetailService) {
        this.jwtAccess = jwtAccess;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

//        String token1 = null;
//        Cookie[] cookies = request.getCookies();
//
//        for (Cookie cookie:cookies){
//            if (cookie.getName().equals("token")){
//                token1 = cookie.getValue();
//            }
//        }
//        logger.info(token1);

        String token = authHeader.substring(7);
        String username = jwtAccess.extractUsername(token);
        if (username !=null && jwtAccess.isTokenValid(token)){
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userDetails != null && authentication == null){
                logger.info(username + " " + "made a request to" + " " + request.getRequestURL() + " " + "with IP:" + request.getRemoteAddr());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                response.setStatus(200);
            }

        }
        filterChain.doFilter(request, response);
    }
}
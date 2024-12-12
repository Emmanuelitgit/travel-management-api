package travel_management_system.Components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JWTAccess {

    private final String SECRETSTRING = "lkjiogjerufgjkfrfrfklweflkwfqejkbqwkdjjbfkjgjfkeyfehj";
    private final String SECRET = Base64.getEncoder().encodeToString(SECRETSTRING.getBytes());
    private final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("issuer", "www.emmanuel.com");
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .setIssuedAt(Date.from(Instant.now()))
                .setSubject(username)
                .signWith(secretKey())
                .compact();
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(){
        return
    }

    private SecretKey secretKey(){
        byte[] secret = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(secret);
    }
}

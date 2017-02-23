package rs.codecentric.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import rs.codecentric.comment.User;
import rs.codecentric.error.SecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class ApiSecurityInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.key}")
    private String jwtKey;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String jwtToken = getToken(request);
        Claims claims = getClaims(jwtToken);
        checkExpiringDate(claims);

        final User user = new User(
                claims.getSubject(),
                claims.get("email", String.class),
                String.format("%s %s", claims.get("firstName"), claims.get("lastName")));
        request.setAttribute("user", user);

        return true;
    }

    private String getToken(HttpServletRequest request) {
        String bearerHeader = request.getHeader("Authorization");
        if (bearerHeader == null || !bearerHeader.startsWith("Bearer ")) {
            throw new SecurityException("Missing or invalid Authorization header.");
        }

        return bearerHeader.substring(7);
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
        } catch (final SignatureException e) {
            throw new SecurityException("Invalid JWT token.");
        }
    }

    private void checkExpiringDate(Claims claims) {
        if (new Date().after(claims.getExpiration())) {
            throw new SecurityException("JWT Token Expired.");
        }
    }

}

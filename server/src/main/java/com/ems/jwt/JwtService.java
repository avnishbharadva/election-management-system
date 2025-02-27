package com.ems.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    public static final String SECRET = "AA76607118989F533AB2D769EB89D882FF30F3B5BFF23097E0B5452D4C80E1F47F2E62ADFCEF528518F93ECADE5E9BB72EB22653011388D5CF89253AE0238B2D";
    public static final long VALIDITY = TimeUnit.MINUTES.toMillis(300);

    public String generateToken(UserDetails userDetails, Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("role", role);

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    public String extractRole(String jwt) {
        return getClaims(jwt).get("role", String.class);
    }

    public Long extractUserId(String jwt) {
        return getClaims(jwt).get("id", Long.class);
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractEmail(String jwt) { // Updated method name
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }


    private Claims getClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey()) // Use correct key
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            return claims.getExpiration().after(Date.from(Instant.now()));
        } catch (Exception e) {
            return false;
        }
    }
}

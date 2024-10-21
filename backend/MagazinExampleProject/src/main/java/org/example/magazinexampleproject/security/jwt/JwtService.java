package org.example.magazinexampleproject.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Component
public class JwtService {

    private static final Logger LOGGER = LogManager.getLogger(JwtService.class);

    @Value("dd5b30ce47f168b479f31e4feeee9fa0a1288a68973595954992d9db8909dd7e")
    private String jwtSecret;

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException ex){
            LOGGER.error("Expired exception", ex);
        }
        catch (UnsupportedJwtException ex){
            LOGGER.error("Unsupported exception", ex);
        }
        catch (MalformedJwtException ex){
            LOGGER.error("Malformed exception", ex);
        }
        catch (SecurityException ex){
            LOGGER.error("Security exception", ex);
        }
        catch (Exception ex){
            LOGGER.error("Invalid token", ex);
        }
        return false;
    }

    public String generateJwtToken(String email, Set<String> role) {
        Date date = Date.from(LocalDateTime.now().plusDays(180).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .claim("roles", role)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }


    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return  Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Set<String> extractRoles(String token) {
        return (Set<String>) extractAllClaims(token).get("roles");
    }
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.okta.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    private final String SECRET_KEY = "secret key";
    //khb7k2jmn4b67k2jn35bl2j4bk5n2b6kn42

    public String extractIdentifier(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String details){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, details);
    }

    private String createToken(Map<String, Object> claims, String subject){
        if (subject == null) {
            throw new IllegalArgumentException("Cannot create JWT with blank subject");
        }
        long time = System.currentTimeMillis();
        long expiry = Duration.ofDays(1).toMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(time))
                .setExpiration(new Date(time + expiry))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String identifier){
        final String phoneNumber = extractIdentifier(token);
        return (phoneNumber.equals(identifier) && !isTokenExpired(token));
    }
}

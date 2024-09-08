package com.example.store.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:jwt.properties")
public class JwtService {

  @Value("${jwt.token.secret-key}")
  private String secretKey;

  public String extractEmail(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  public <T> T extractClaim(String jwt, Function<Claims, T> claimsFunction) {
    final Claims claims = extractClaims(jwt);
    return claimsFunction.apply(claims);
  }

  public String generateJwtToken(UserDetails userDetails) {
    return generateJwtToken(new HashMap<>(), userDetails);
  }

  public String generateJwtToken(Map<String, Object> claims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 86500))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isJwtTokenValid(String jwt, UserDetails userDetails) {
    final String email = extractEmail(jwt);
    return (email.equals(userDetails.getUsername())) && !isJwtTokenExpired(jwt);
  }

  private boolean isJwtTokenExpired(String jwt) {
    return getExpirationDateFromJwtToken(jwt).before(new Date());
  }

  private Date getExpirationDateFromJwtToken(String jwt) {
    return extractClaim(jwt, Claims::getExpiration);
  }

  private Claims extractClaims(String jwt) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

  private Key getSigningKey() {
    byte[] key = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(key);
  }
}

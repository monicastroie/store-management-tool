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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String SECRET_KEY = "7a88f285b330ac44dae66010385842e09b34d80a020f7e4ff51970732906cd57";

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
        .parseClaimsJwt(jwt)
        .getBody();
  }

  private Key getSigningKey() {
    byte[] secretKey = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(secretKey);
  }
}

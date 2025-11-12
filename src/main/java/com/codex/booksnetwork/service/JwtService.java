package com.codex.booksnetwork.service;

import com.codex.booksnetwork.config.JwtConfig;
import com.codex.booksnetwork.entity.User;
import com.codex.booksnetwork.utils.Jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
  private final JwtConfig jwtConfig;

  public Jwt generateAccessToken(User user) {
    log.info("getAccessTokenExpiration user: {}", jwtConfig.getAccessTokenExpiration());
    return generateToken(user, jwtConfig.getAccessTokenExpiration());
  }

  public Jwt generateRefreshToken(User user) {
    log.info("getRefreshTokenExpiration user: {}", jwtConfig.getRefreshTokenExpiration());
    return generateToken(user, jwtConfig.getRefreshTokenExpiration());
  }

  public Jwt parseToken(String token){

    try{
      var claims = getClaims(token);
      return new Jwt(claims, jwtConfig.getSecretKey());
    }catch (JwtException e){
      return null;
    }


  }

  private Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(jwtConfig.getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private Jwt generateToken(User user, long tokenExpiration) {
    var authorities = user
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
    log.info("authorities: {}", authorities);
    Claims claims =
        Jwts.claims()
            .subject(user.getId().toString())
            .add("username", user.getUsername())
            .add("authorities", authorities)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
            .build();

    return new Jwt(claims, jwtConfig.getSecretKey());
  }

}

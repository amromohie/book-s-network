package com.codex.booksnetwork.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class Jwt {

  private final Claims claims;
  private final SecretKey secretKey;


  public boolean isExpired(String token) {
    return claims.getExpiration().before(new Date());
  }


  public Long getUserId() {
    return Long.valueOf(claims.getSubject());
  }

  public String getUsername() {
    return claims.get("username", String.class);
  }


  public Collection<GrantedAuthority> getAuthorities() {
    var roles = claims.get("authorities", ArrayList.class);
    return roles.stream()
        .map(role -> (GrantedAuthority) () -> role.toString())
        .toList();
  }


  @Override
  public String toString() {
    return Jwts.builder().claims(claims).signWith(secretKey).compact();
  }




}

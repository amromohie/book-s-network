package com.codex.booksnetwork.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.codex.booksnetwork.service.JwtService;
import com.codex.booksnetwork.utils.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getServletPath().contains("api/v1/auth")){
      filterChain.doFilter(request, response);
      return;
    }

    var authHeader = request.getHeader(AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    var token = authHeader.replace("Bearer ", "");

    Jwt jwt = jwtService.parseToken(token);

    if (jwt == null || jwt.isExpired(token)) {
      filterChain.doFilter(request, response);
      return;
    }



    var authentication =
        new UsernamePasswordAuthenticationToken(
            jwt.getUserId(),
            null,
            jwt.getAuthorities()
            );

    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}

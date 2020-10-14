package com.project.bgt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public static final String HEADER = "Authorization";
  public static final String PREFIX = "Bearer ";
  public static final String SECRET = "somesecret";
  public static final int VALIDITY = 60 * 60 * 1000;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain chain) throws ServletException, IOException {
    String requestTokenHeader = request.getHeader(HEADER);

    if (requestTokenHeader != null && requestTokenHeader.startsWith(PREFIX)) {
      String jwtToken = requestTokenHeader.substring(PREFIX.length());
      Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken).getBody();
      String username = claims.getSubject();
      Date tokenExpirationDate = claims.getExpiration();

      if (tokenExpirationDate.after(new Date())) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
          new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(request, response);
  }
}

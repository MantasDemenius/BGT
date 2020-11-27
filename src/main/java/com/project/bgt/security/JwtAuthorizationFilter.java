package com.project.bgt.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  public static final String HEADER = "Authorization";
  public static final String PREFIX = "Bearer ";
  public static final String SECRET = "somesecret";
  public static final int VALIDITY = 60 * 60 * 1000;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain chain) throws ServletException, IOException {
    String requestTokenHeader = request.getHeader(HEADER);

    if (requestTokenHeader != null && requestTokenHeader.startsWith(PREFIX)) {
      String jwtToken = requestTokenHeader.substring(PREFIX.length());
      String username = tokenProvider.getUsernameFromToken(jwtToken);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = securityService.loadUserByUsername(username);

        if (tokenProvider.validateToken(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = tokenProvider
            .getAuthentication(jwtToken, SecurityContextHolder.getContext().getAuthentication(),
              userDetails);
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }
}

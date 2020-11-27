package com.project.bgt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException ex) throws IOException {

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}

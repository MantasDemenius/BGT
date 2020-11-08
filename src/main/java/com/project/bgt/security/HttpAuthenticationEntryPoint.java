package com.project.bgt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bgt.exception.ServerError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
//    ServerError serverError = new ServerError(ex.getMessage(), new ArrayList<>());
//
//    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    response.setContentType("application/json");
//
//    PrintWriter writer = response.getWriter();
//    objectMapper.writeValue(writer, serverError);
//    writer.flush();

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
  }
}

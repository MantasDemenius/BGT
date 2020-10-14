package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.security.JwtAuthorizationFilter;
import com.project.bgt.security.SecurityService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.PATH)
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private SecurityService userDetailsService;

  @PostMapping(value = "/authenticate")
  public String authenticate(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return generateToken(userDetails);
  }


  private String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + JwtAuthorizationFilter.VALIDITY))
      .signWith(SignatureAlgorithm.HS512, JwtAuthorizationFilter.SECRET)
      .compact();
  }
}

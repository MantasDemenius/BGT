package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.model.AuthToken;
import com.project.bgt.security.SecurityService;
import com.project.bgt.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.PATH)
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private SecurityService userDetailsService;

  @Autowired
  private TokenProvider tokenProvider;

  @PostMapping(value = "/authenticate")
  public ResponseEntity authenticate(@RequestParam(value = "username") String username,
    @RequestParam(value = "password") String password) {

    final Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = tokenProvider.generateToken(authentication);
//    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return ResponseEntity.ok(new AuthToken(token));
  }

//  private String generateToken(UserDetails userDetails) {
////    Map<String, Object> claims = new HashMap<>();
//    return Jwts.builder()
////      .setClaims(claims)
//      .setSubject(userDetails.getUsername())
//      .setIssuedAt(new Date(System.currentTimeMillis()))
//      .setExpiration(new Date(System.currentTimeMillis() + JwtAuthorizationFilter.VALIDITY))
//      .signWith(SignatureAlgorithm.HS512, JwtAuthorizationFilter.SECRET)
//      .compact();
//  }
}

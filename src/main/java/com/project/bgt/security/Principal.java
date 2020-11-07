package com.project.bgt.security;

import com.project.bgt.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class Principal implements UserDetails {

  private User user;
  private Collection<? extends GrantedAuthority> authorities;

  public Principal(User user, Collection<? extends GrantedAuthority> authorities) {
    this.user = user;
    this.authorities = authorities;
  }

//  public static Principal create(User user) {
//    List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
//      new SimpleGrantedAuthority(role.getName().name())
//    ).collect(Collectors.toList());
//
//    return new Principal(
//      user,
//      authorities
//    );
//  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}

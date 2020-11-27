package com.project.bgt.security;

import com.project.bgt.model.User;
import com.project.bgt.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(
        String.format("User with username \"%s\" not found.", username));
    }
    return new Principal(user, getAuthority(user));
  }

  private Set<GrantedAuthority> getAuthority(User user) {
    Set<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
      new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toSet());

    return authorities;
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername((String) authentication.getPrincipal());
  }
}

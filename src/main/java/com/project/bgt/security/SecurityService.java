package com.project.bgt.security;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.model.User;
import com.project.bgt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    if(user == null){
      throw new UsernameNotFoundException(String.format("User with username \"%s\" not found.", username));
    }

    return new Principal(user);
  }

  public ResponseEntity createUser(UserDTO userDTO) {
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

    User newUser = userRepository.save(new User(
      userDTO.getUsername(),
      userDTO.getEmail(),
      userDTO.getPassword(),
      userDTO.getUserRole()
    ));

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.USER_PATH, newUser.getId()),
      HttpStatus.CREATED);
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername((String) authentication.getPrincipal());
  }
}

package com.project.bgt.security;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.exception.ResponseEntityBuilder;
import com.project.bgt.model.ApiError;
import com.project.bgt.model.Role;
import com.project.bgt.model.User;
import com.project.bgt.model.UserRoleName;
import com.project.bgt.repository.RoleRepository;
import com.project.bgt.repository.UserRepository;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

@Service
public class SecurityService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if(user == null){
      throw new UsernameNotFoundException(String.format("User with username \"%s\" not found.", username));
    }

    return new Principal(user, getAuthority(user));
  }

  private Set<GrantedAuthority> getAuthority(User user) {
    Set<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
      new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toSet());

    return authorities;
  }

  public ResponseEntity createUser(UserDTO userDTO) {
    if(userRepository.existsByUsername(userDTO.getUsername())){
      List<String> errors = new ArrayList<String>();
      errors.add("Username is already taken!");
      return  ResponseEntityBuilder.build(new ApiError(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST,
        errors));
    }

    if(userRepository.existsByEmail(userDTO.getEmail())){
      List<String> errors = new ArrayList<String>();
      errors.add("Email is already taken!");
      return  ResponseEntityBuilder.build(new ApiError(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST,
        errors));
    }

    User user = new User(
      userDTO.getUsername(),
      userDTO.getEmail(),
      passwordEncoder.encode(userDTO.getPassword())
    );

    Role userRole = roleRepository.findByName(UserRoleName.BASIC)
      .orElseThrow(() -> new RecordNotFoundException("Role was not found!"));

    user.setRoles(Collections.singleton(userRole));

    User newUser = userRepository.save(user);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.USER_PATH, newUser.getId()),
      HttpStatus.CREATED);
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername((String) authentication.getPrincipal());
  }
}

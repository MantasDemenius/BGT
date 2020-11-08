package com.project.bgt.repository;

import com.project.bgt.model.Role;
import com.project.bgt.model.User;
import com.project.bgt.model.UserRoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


  Optional<Role> findByName(UserRoleName basic);
}

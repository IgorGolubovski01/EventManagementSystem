package com.example.EventManagementSystem.repository;

import com.example.EventManagementSystem.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


    Role findByRoleName(String user);
}

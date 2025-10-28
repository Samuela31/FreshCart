package com.samuela.grocery.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuela.grocery.dao.entity.RoleDetails;

@Repository
public interface RoleDetailsDao extends JpaRepository<RoleDetails, Integer> {
    Optional<RoleDetails> findByRoleName(String roleName);
}
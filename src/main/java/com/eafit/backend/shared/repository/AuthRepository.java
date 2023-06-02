package com.eafit.backend.shared.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eafit.backend.shared.entity.Auth;
import com.eafit.backend.shared.entity.Usuario;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

    Optional<Auth> findByUserId(String id);
}

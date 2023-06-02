package com.eafit.backend.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eafit.backend.shared.entity.Operador;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Integer> {
}

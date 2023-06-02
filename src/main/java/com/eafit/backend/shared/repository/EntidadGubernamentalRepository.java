package com.eafit.backend.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eafit.backend.shared.entity.EntidadGubernamental;

@Repository
public interface EntidadGubernamentalRepository extends JpaRepository<EntidadGubernamental, Integer> {
}

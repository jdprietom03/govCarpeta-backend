package com.eafit.backend.shared.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario {
    @Id
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Operador operador;
    
    private String name;
    private String email;
    private String address;
    private String role;
}
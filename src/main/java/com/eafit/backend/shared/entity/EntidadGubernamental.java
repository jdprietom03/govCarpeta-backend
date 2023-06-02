package com.eafit.backend.shared.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "EntidadGubernamental")
public class EntidadGubernamental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Operador operador;
    
    private String nombre;
    private String correo_electronico;
    private String direccion;
}

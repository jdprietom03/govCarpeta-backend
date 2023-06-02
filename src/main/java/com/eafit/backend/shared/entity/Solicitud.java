package com.eafit.backend.shared.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Solicitud")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "entidad_id")
    private EntidadGubernamental entidad;
    
    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Operador operador;
    
    private Date fecha_solicitud;
    private String estado_solicitud;
}

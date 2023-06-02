package com.eafit.backend.shared.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Operador")
public class Operador {
    @Id
    private String operatorId;
    private String operatorName;
    private String host;
    private Integer port;
    private String ip;
}

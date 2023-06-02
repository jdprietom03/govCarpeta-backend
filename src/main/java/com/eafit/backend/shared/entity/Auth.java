package com.eafit.backend.shared.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Auth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario user;
    
    private String hash;
    private String salt;
}

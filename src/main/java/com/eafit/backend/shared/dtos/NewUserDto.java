package com.eafit.backend.shared.dtos;

import com.eafit.backend.shared.entity.Usuario;

public record NewUserDto(String id, String name, String email, String address, String password, Operator operador) {
    public Usuario toEntity() {
        Usuario user = new Usuario();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setRole("USER");
        user.setAddress(address);
        user.setOperador(operador.toEntity());

        return user;
    }
}
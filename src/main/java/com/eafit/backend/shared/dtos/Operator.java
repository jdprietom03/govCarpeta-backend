package com.eafit.backend.shared.dtos;

import com.eafit.backend.shared.entity.Operador;

public record Operator(String operatorId, String operatorName) {
    public Operador toEntity() {
        Operador operator = new Operador();
        operator.setOperatorId(operatorId);
        operator.setOperatorName(operatorName);

        return operator;
    }
}

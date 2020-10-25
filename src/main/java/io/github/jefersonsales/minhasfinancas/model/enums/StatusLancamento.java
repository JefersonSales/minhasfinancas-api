package io.github.jefersonsales.minhasfinancas.model.enums;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusLancamento {

    PENDENTE(1, "Pendente"),
    CANCELADO(2, "Cancelado"),
    EFETIVADO(3, "Efetivado");

    private Integer codigo;
    private String description;

}

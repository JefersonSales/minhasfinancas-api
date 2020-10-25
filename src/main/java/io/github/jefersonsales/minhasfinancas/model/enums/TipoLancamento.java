package io.github.jefersonsales.minhasfinancas.model.enums;

import lombok.AllArgsConstructor;


public enum TipoLancamento {

    RECEITA(1, "Receita"),
    DESPESA(2, "Despesa");

    private Integer codigo;
    private String description;

    //O @AllArgsConstructor faz a mesma coisa que esse construtor
    TipoLancamento(Integer codigo, String description) {
       this.codigo = codigo;
       this.description = description;
    }
}

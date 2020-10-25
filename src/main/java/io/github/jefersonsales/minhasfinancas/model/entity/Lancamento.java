package io.github.jefersonsales.minhasfinancas.model.entity;

import io.github.jefersonsales.minhasfinancas.model.enums.StatusLancamento;
import io.github.jefersonsales.minhasfinancas.model.enums.TipoLancamento;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Table(name = "lancamento", schema = "financas")
@Setter
@Getter
public class Lancamento {

    public Lancamento() {
    }

    public Lancamento(Long id, String descricao, Integer mes, Integer ano, Usuario usuario, BigDecimal valor, Calendar dataCadastro, TipoLancamento tipo, StatusLancamento status) {
        this.id = id;
        this.descricao = descricao;
        this.mes = mes;
        this.ano = ano;
        this.usuario = usuario;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
        this.tipo = tipo;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Calendar dataCadastro;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;

}

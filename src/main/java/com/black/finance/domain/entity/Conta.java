package com.black.finance.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contas")
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contaID")
    private Integer id;

    @Column(name = "contaDesc")
    private String contaDesc;

    @ManyToOne
    @JoinColumn(name = "bcCodigo")
    private Banco banco;

    @Column(name = "agencia", length = 50)
    @NotEmpty(message = "campo.agencia.obrigatorio")
    private String agencia;

    @Column(name = "conta", length = 50)
    @NotEmpty(message = "campo.conta.obrigatorio")
    private String conta;

    @Column(name = "saldoAtual")
    private BigDecimal saldoAtual;

    @Column(name = "valManutencao")
    private BigDecimal valManutencao;

    @Column(name = "dataManutencao")
    private Integer diaCobManutencao;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Usuario usuario;
}

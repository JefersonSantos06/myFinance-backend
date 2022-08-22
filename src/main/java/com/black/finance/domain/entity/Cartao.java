package com.black.finance.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cartoes")
public class Cartao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartaoID")
    private Integer id;

    @Column(name = "cartaoDesc")
    private String cartaoDesc;

    @Column(name = "diaVencimento")
    private Integer diaVencimento;

    @Column(name = "anuidade")
    private BigDecimal anuidade;

    @Column(name = "bandeira")
    private String bandeira;

    @Column(name = "vencimentoCartao")
    private String vencimentoCartao;

    @Column(name = "ult4Digitos")
    private String ult4Digitos;

    @ManyToOne
    @JoinColumn(name = "bcCodigo")
    private Banco banco;

    @Column(name = "limite")
    private BigDecimal limite;

    @Column(name = "limiteUtilizado")
    private BigDecimal limiteUtilizado;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Usuario usuario;
}
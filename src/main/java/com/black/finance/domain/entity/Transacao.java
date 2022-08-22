package com.black.finance.domain.entity;

import com.black.finance.domain.enums.TipoPagamento;
import com.black.finance.domain.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private BigDecimal valor;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipoTransacao")
    private TipoTransacao tipoTransacao;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipoPagamento")
    private TipoPagamento tipoPagamento;

    @Column(name = "dtTransacao")
    private LocalDate dtTransacao;

    @Column(name = "numParcela")
    private Integer numParcela;

    @Column(name = "dtEntSistema")
    private LocalDate dtEntSistema;

    @ManyToOne
    @JoinColumn(name = "contaID")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "cartaoID")
    private Cartao cartao;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Usuario usuario;

}

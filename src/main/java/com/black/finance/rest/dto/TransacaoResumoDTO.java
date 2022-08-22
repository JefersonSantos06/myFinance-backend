package com.black.finance.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResumoDTO {
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private String tipoTransacao;
    private String tipoPagamento;
    private String dtTransacao;
    private Integer numParcela;
    private String dtEntSistema;
}

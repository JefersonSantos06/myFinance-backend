package com.black.finance.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

    private Integer id;

    @NotEmpty(message = "campo.descricao.obrigatorio")
    private String descricao;

    @NotNull(message = "campo.valor.obrigatorio")
    private BigDecimal valor;

    private String tipoTransacao;

    private String tipoPagamento;

    private String dtTransacao;

    private Integer numParcela;

    private String dtEntSistema;

    private Integer contaID;

    private Integer cartaoID;

}

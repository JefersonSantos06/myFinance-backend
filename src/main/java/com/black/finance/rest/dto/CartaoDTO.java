package com.black.finance.rest.dto;

import com.black.finance.domain.entity.Banco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartaoDTO {

    private Integer id;

    @NotEmpty(message = "campo.cartaoDesc.obrigatorio")
    private String cartaoDesc;

    private Integer diaVencimento;

    private BigDecimal anuidade;

    private Banco banco;

    private BigDecimal limite;

    private BigDecimal limiteUtilizado;

    private String bandeira;

    private String vencimentoCartao;

    private String ult4Digitos;

}

package com.black.finance.rest.dto;

import com.black.finance.domain.entity.Banco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDTO {

    private Integer id;

    @NotEmpty(message = "campo.bcCodigo.obrigatorio")
    private String banco;

    @NotEmpty(message = "campo.agencia.obrigatorio")
    private String agencia;

    @NotEmpty(message = "campo.conta.obrigatorio")
    private String conta;

    @NotEmpty(message = "campo.descricao.obrigatorio")
    private String contaDesc;

    private BigDecimal saldoAtual;

    private BigDecimal valManutencao;

    private Integer diaCobManutencao;

}

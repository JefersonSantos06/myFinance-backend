package com.black.finance.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BancoDTO {

    @JsonIgnore
    private Integer id;

    @NotEmpty(message = "{campo.bcCodigo.obrigatorio}")
    private String bcCodigo;

    @NotEmpty(message = "{campo.bcNome.obrigatorio}")
    private String bancoNome;

    public BancoDTO(String bcCodigo, String bancoNome) {
        this.bcCodigo = bcCodigo;
        this.bancoNome = bancoNome;
    }
}

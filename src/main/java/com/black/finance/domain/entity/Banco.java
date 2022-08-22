package com.black.finance.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bancos")
@Builder
public class Banco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Integer id;

    @Column(name = "bcCodigo", length = 10, unique = true)
    @NotEmpty(message = "${campo.bcCodigo.obrigatorio}")
    private String bcCodigo;

    @Column(name = "bcNome", length = 50)
    @NotNull(message = "campo.bcNome.obrigatorio")
    private String bancoNome;

}

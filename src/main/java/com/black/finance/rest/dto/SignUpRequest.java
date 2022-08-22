package com.black.finance.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String name;

    @NotEmpty(message = "Username é obrigatorio")
    private String username;

    @NotEmpty(message = "Password é obrigatorio")
    private String password;

}

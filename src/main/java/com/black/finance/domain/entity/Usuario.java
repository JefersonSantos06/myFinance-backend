package com.black.finance.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "${campo.username.obrigatorio}")
    private String username;

    @NotEmpty(message = "Password é obrigatorio")
    private String password;


    public Usuario(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }



}
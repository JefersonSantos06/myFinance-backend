package com.black.finance.domain.repository;

import com.black.finance.domain.entity.Conta;
import com.black.finance.domain.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Contas extends JpaRepository<Conta, Integer> {

    Optional<Conta> findByIdAndUsuario(Integer id, Usuario usuario);

    Page<Conta> findAllByUsuario(Usuario usuario, Pageable pageable);
}

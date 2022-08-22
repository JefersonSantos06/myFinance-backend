package com.black.finance.domain.repository;

import com.black.finance.domain.entity.Cartao;
import com.black.finance.domain.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Cartoes extends JpaRepository<Cartao, Integer> {

    Optional<Cartao> findByIdAndUsuario(Integer id, Usuario usuario);

    Page<Cartao> findAllByUsuario(Usuario usuario, Pageable pageable);

}

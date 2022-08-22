package com.black.finance.domain.repository;

import com.black.finance.domain.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Bancos extends JpaRepository<Banco, Integer> {

    Optional<Banco> findByBcCodigo(String bcCodigo);
    boolean existsByBcCodigo(String bcCodigo);

}

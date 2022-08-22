package com.black.finance.domain.repository;

import com.black.finance.domain.entity.Transacao;
import com.black.finance.domain.entity.Usuario;
import com.black.finance.domain.enums.TipoTransacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Transacoes extends JpaRepository<Transacao, Integer> {

    Optional<Transacao> findByIdAndUsuarioAndTipoTransacao(Integer id, Usuario usuario, TipoTransacao tipoTransacao);

    Optional<Transacao> findByIdAndUsuario(Integer id, Usuario usuario);

    Page<Transacao> findAllByUsuario(Usuario usuario, Pageable pageable);

    Page<Transacao> findAllByUsuarioAndTipoTransacao(Usuario usuario, TipoTransacao tipo, Pageable pageable);

    @Query(value = " select * from transacoes t where t.userID =:user and t.dt_transacao like :mesAno%",
            countQuery = "SELECT count(*) FROM transacoes t WHERE t.userID =:user and t.dt_transacao like :mesAno%",
            nativeQuery = true)
    Page<Transacao> pegaTransacaoPorUsuaioEMes(@Param("user") Long userID,@Param("mesAno") String mesAno, Pageable pageable);
}

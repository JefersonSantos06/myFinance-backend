package com.black.finance.service;

import com.black.finance.domain.enums.TipoTransacao;
import com.black.finance.rest.dto.TransacaoDTO;
import com.black.finance.rest.dto.TransacaoResumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransacaoService {

    TransacaoDTO create(TransacaoDTO dto);

    TransacaoDTO findById(Integer id, TipoTransacao tipoTransacao);

    Page<TransacaoDTO> findAll(TipoTransacao tipoTransacao, Pageable pageable);

    Page<TransacaoResumoDTO> findTransacaoByUsuarioNoMesX(String mesAndAno,Pageable pageable);

    void delete(Integer id);

    TransacaoDTO update(TransacaoDTO dto, Integer id);
}
